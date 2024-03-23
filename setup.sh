mkdir game_binaries
cd game_binaries
wget -P . https://ocw.cs.pub.ro/courses/_media/pa/halite-resources-2024.zip
sudo apt install unzip
unzip halite-resources-2024.zip
cd environment
make
cp halite ../halite
cd ../../
