from pathlib import Path
from os import listdir
import matplotlib.pyplot as plt
import numpy as np

objects = []
values = []

path = "../DiningPhilosophersProblem-Java"
files = listdir(path)
for file in files:
    if file.startswith("starving"):
        print("Reading file:", file)
        with open(file, 'r') as f:
            lines = f.readlines()
        i = 0
        for line in lines:
            values.append(float(line.strip()))
            objects.append('Filozof ' + str(i))
            i += 1
print(values, objects)
y_pos = np.arange(len(objects))
plt.bar(y_pos, values)
plt.xticks(y_pos, objects)
plt.ylabel('Średni czas oczekiwania [ms]')
plt.title('Porównanie średnich czasów oczekiwania na posiłek')

plt.show()
