package threadsafecounter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestCounter {

    public static void main(String[] args) throws InterruptedException {

        List<Integer> list = List.of(1,2,3,4,5);
        list.forEach(System.out::println);

        Predicate<Integer> isOdd = (i)->i%2!=0;

        for (Integer i: list){
            if(isOdd.test(i)){
                System.out.println(i+" is ODD");
            }
        }

        Thread thread = new Thread();
        thread.start();
        
        Runnable r = System.out::println;
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);

        AtomicCounter c = new AtomicCounter();

        Runnable counter = () -> {
            for(int i=0; i<100; i++){
                c.increment();
            }
        };

        Consumer<Integer> checkEven = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                if(integer%2==0)
                System.out.println(integer+" is Even");
            }
        };

        list.forEach(checkEven);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(counter);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Final Count: " + c.getValue());

    }

}
