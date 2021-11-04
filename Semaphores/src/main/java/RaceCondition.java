public class RaceCondition {

    public static void main(String[] args) {
        int n = 100000;

        // 1.1
        ISemaphore sem = new Semaphore(true);
        Counter counter = new Counter(0, sem);

        Runnable incr = () -> {
            try{
                for (int i = 0; i < n; i++) {
                    counter.increment();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
                System.exit(-1);
            }

        };

        Runnable decr = () -> {
            try {
                for (int i = 0; i < n; i++) {
                    counter.decrement();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
                System.exit(-1);
            }
        };

        Thread thread1 = new Thread(incr);
        Thread thread2 = new Thread(decr);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Counter: " + counter.getCounter());

        // 1.2
        /*
        Do implementacji semafora za pomocą metod wait i notify nie wystarczy instrukcja if, tylko potrzeba użyć while,
        ponieważ mogłaby się zdarzyć taka sytuacja, że dany wątek zostanie obudzony, gdy nie jest spełniony warunek,
        na którym czeka. Wtedy, gdyby tam było samo if, to wątek zostałby wpuszczony do sekcji krytycznej, chociaż nie
        powinien, bo w tamtym ifie zobaczył on, że semafor jest podniesiony, a nie zdążył zobaczyć, że już ktoś przed
        nim go opuścił. Żeby takiej sytuacji zapobiec, używa się pętli while zamiast samego if, która umożliwia
        ponowne sprawdzenie, czy warunek faktycznie jest spełniony w momencie obudzenia wątku.

        Praktyczny przykład:
        2 wątki dzielące licznik w counterze, jeden go zwiększa a drugi go zmniejsza n-razy.
        W takiej sytuacji bardzo często końcowa wartość countera jest różna od wartości, jaką miał na początku,
        chociaż nie powinna. Wartość countera inna niż jego początkowa wartość świadczy o tym, że operacja P na semaforze
        wpuściła do sekcji krytycznej obydwa wątki, chociaż nie powinna. Powinna była obudzić wyłącznie jeden z nich,
        a drugi powinien nadal pozostać uśpiony. W takiej sytuacji następuje wyścig, ponieważ 2 wątki korzystają
        jednocześnie z dzielonego zasobu i chcą go zmienić. Poprawnie zaimplementowany semafor z użyciem while zamiast
        if by nie dopuścił do wyścigu, a tym samym wartość counter by została zwiększona tyle razy, ile razy byłaby
        zmniejszona, więc na końcu pozostałaby niezmieniona. Niekiedy nawet występuje zakleszczenie obydwu
        czekających wątków, czemu jest w stanie zapobiec użycie pętli while zamiast if.
         */

        ISemaphore sem2 = new IncorrectSemaphore(true);
        Counter counter2 = new Counter(0, sem2);

        Runnable incr2 = () -> {
            try{
                for (int i = 0; i < n; i++) {
                    counter2.increment();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
                System.exit(-1);
            }

        };

        Runnable decr2 = () -> {
            try {
                for (int i = 0; i < n; i++) {
                    counter2.decrement();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
                System.exit(-1);
            }
        };

        Thread thread1_2 = new Thread(incr2);
        Thread thread2_2 = new Thread(decr2);

        thread1_2.start();
        thread2_2.start();

        try {
            thread1_2.join();
            thread2_2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Counter: " + counter2.getCounter());

        // 1.3
        /*
         Semafor binarny jest szczególnym przypadkiem semafora ogólnego, ponieważ
         działa on jak semafor ogólny, synchronizując dostęp do jednego współdzielonego zasobu.
         Natomiast semafor ogólny może synchronizować dostęp do dowolnej ilości współdzielonych zasobów.

         Przykład praktyczny:
         Realizacja semafora binarnego wykorzystywanego do programu wyścig za pomocą semafora ogólnego.
         Działa on poprawnie, ponieważ wartość counter na końcu wynosi 0, czyli tyle ile na początku.
         */
        CountingSemaphore sem3 = new CountingSemaphore(1);
        Counter counter3 = new Counter(0, sem3);

        Runnable incr3 = () -> {
            try{
                for (int i = 0; i < n; i++) {
                    counter3.increment();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
                System.exit(-1);
            }

        };

        Runnable decr3 = () -> {
            try {
                for (int i = 0; i < n; i++) {
                    counter3.decrement();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
                System.exit(-1);
            }
        };

        Thread thread1_3 = new Thread(incr3);
        Thread thread2_3 = new Thread(decr3);

        thread1_3.start();
        thread2_3.start();

        try {
            thread1_3.join();
            thread2_3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Counter: " + counter3.getCounter());
    }
}
