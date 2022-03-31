from multiprocessing import Pool


def merge(t):
    res = []
    tab1 = t[0]
    tab2 = t[1]
    p = 0
    q = 0
    while p < len(tab1) and q < len(tab2):
        if tab1[p] < tab2[q]:
            res.append(tab1[p])
            p += 1
        else:
            res.append(tab2[q])
            q += 1
    if p < len(tab1):
        res.extend(tab1[p:])
    elif q < len(tab2):
        res.extend(tab2[q:])
    return res


if __name__ == '__main__':
    tab = [5, 6, 1, 8, 2, 3, 7, 4]
    processes = len(tab)
    tab = [tab[i:i+1] for i in range(0, len(tab))]  # zrobienie z pojedynczej listy listy list o długości 1 np. [[1], [5], [2], [3]]
    while processes > 1:
        processes = processes // 2  # W każdej iteracji scalania liczba procesów będzie maleć 2 krotnie
        tab = [tab[i:i + 2] for i in range(0, len(tab), 2)]  # podzielenie listy na pary elementów do scalenia np z powyższej listy w pierwszej iteracji będzie [[[1], [5]], [[2], [3]]]
        # print(processes, tab)
        p = Pool(processes)
        tab = p.map(merge, tab)  # wynik pojedynczej iteracji
        p.close()
        p.join()
    tab = tab[0]
    print(tab)
