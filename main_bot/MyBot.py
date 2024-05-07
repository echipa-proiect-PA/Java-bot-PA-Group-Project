import numpy

import hlt
from hlt import NORTH, EAST, SOUTH, WEST, STILL, Move, Square, GameMap
import random
from typing import List
import math
import numpy as np

LOGS_ENABLED = False
LOG_FILE_PATH = "log-pa_bot.txt"
# NORTH, EAST, SOUTH, WEST, STILL
DIRS_DIFF = ((0, -1), (1, 0), (0, 1), (-1, 0), (0, 0))
DIRS_BY_DIFF = {(0, -1): NORTH, (1, 0): EAST, (0, 1): SOUTH, (-1, 0): WEST, (0, 0): STILL}

myID, my_game_map = hlt.get_init()
hlt.send_init("Terminator")

if LOGS_ENABLED:
    with open(LOG_FILE_PATH, 'w') as file:
        file.write("Begin logs\n")


class TurnPrecomputedData:
    def __init__(self, game_map: GameMap):
        self.not_owned = compute_not_owned_squares(game_map)


def log(*args):
    """logs a message to LOG_FILE_PATH if LOGS_ENABLED is true"""
    if LOGS_ENABLED:
        with open(LOG_FILE_PATH, 'a') as file:
            [file.write(str(arg) + " ") for arg in args]
            file.write("\n")


def normalize(v: np.array) -> np.array:
    """given a vector v returns the normalized vector"""
    norm = np.linalg.norm(v)
    if norm == 0:
        norm = np.finfo(v.dtype).eps
    return v / norm


def find_max_production_square(game_map: GameMap) -> Square:
    """returns the square with the biggest production in the map"""
    return max(game_map, key=lambda square: square.production)


def compute_closest_direction(dx: float, dy: float) -> int:
    """given a difference in coordinates, computes the move in the closest direction"""
    v = normalize(np.array([dx, dy], dtype=np.float32))
    diff = max(DIRS_DIFF, key=lambda direction: np.dot(v, normalize(np.array(direction, dtype=np.float32))))
    return DIRS_BY_DIFF[diff]


def compute_move(width: int, height: int, start: Square, target: Square) -> Move:
    """computes a valid move from start towards target"""
    dx = target.x - start.x
    dy = target.y - start.y
    # minimum dx and dy considering the map is circular
    dx = np.sign(dx) * min(abs(dx), abs(width - dx) / 2)
    dy = np.sign(dy) * min(abs(dy), abs(height - dy) / 2)
    direction = compute_closest_direction(dx, dy)
    return Move(start, direction)


def compute_not_owned_squares(game_map: GameMap) -> List[Square]:
    """returns the squares in the map which are not owned by the bot"""
    return [square for square in game_map if square.owner != myID]


def closest_not_owned_square(game_map: GameMap, square: Square, not_owned: List[Square]) -> Square or None:
    """returns the closest square not owned by the bot"""
    if len(not_owned) > 0:
        return min(not_owned, key=lambda x: game_map.get_distance(square, x))
    return None


def decide_move(game_map: GameMap, square: Square, turn_data: TurnPrecomputedData) -> Move:
    """given the game map and a square returns the next move for the square"""
    neighbours: List[Square] = list(game_map.neighbors(square, 1))
    min_neighbour_strength = min([neighbour.strength for neighbour in neighbours])
    move_to_make = Move(square, STILL)
    if min_neighbour_strength < square.strength:
        not_owned = list(filter(lambda x: x.owner != myID, neighbours))
        if len(not_owned) == 0:
            closest_sq = closest_not_owned_square(game_map, square, turn_data.not_owned)
            if closest_sq:
                move_to_make = compute_move(game_map.width, game_map.height, square, closest_sq)
            else:
                move_to = random.choice(neighbours)
                move_to_make = compute_move(game_map.width, game_map.height, square, move_to)
        else:
            lower = list(filter(lambda x: x.strength <= square.strength, not_owned))
            ascending_strengths = sorted(lower, key=lambda x: x.strength)
            if len(ascending_strengths) > 0:
                move_to = ascending_strengths[-1]
                move_to_make = compute_move(game_map.width, game_map.height, square, move_to)
    return move_to_make


max_square = find_max_production_square(my_game_map)
log("max square: " + str(max_square))

while True:
    my_game_map.get_frame()
    current_turn_data = TurnPrecomputedData(my_game_map)
    moves = [decide_move(my_game_map, square, current_turn_data) for square in my_game_map if
             square.owner == myID]
    hlt.send_frame(moves)
