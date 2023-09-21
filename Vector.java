package com.company;
import java.util.Random;

public class Vector {
    static int[] a;
    static int[] b;

    static void Eq(int start, int end) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j <= i; j++) {
                b[i] += a[i] * 2;
            }
        }
    }

    static void Circ(int iThread, int M) {
        for (int i = iThread; i < b.length; i += M) {
            for (int j = 0; j <= i; j++) {
                b[i] += a[i] * 2;
            }
        }
    }

    static String Single_Test(int N) {
        a = new int[N];
        b = new int[N];
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            a[i] = rand.nextInt(10);
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Eq(0, N);
        }
        long endTime = System.currentTimeMillis();
        double timeInSeconds = (endTime - startTime) / 1000.0;
        return String.format("%d элементов / Один поток / %.3f секунд", N, timeInSeconds);
    }

    static String Multi_Test(int N, int numThreads) {
        int M = numThreads;
        a = new int[N];
        b = new int[N];
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            a[i] = rand.nextInt(10);
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Thread[] arrThr = new Thread[M];
            for (int j = 0; j < M; j++) {
                int finalJ = j;
                arrThr[j] = new Thread(() -> Circ(finalJ, M));
                arrThr[j].start();
            }
            for (int j = 0; j < M; j++) {
                try {
                    arrThr[j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        long endTime = System.currentTimeMillis();
        double timeInSeconds = (endTime - startTime) / 1000.0;
        return String.format("%d элементов / %d потоков / %.3f секунд", N, M, timeInSeconds);
    }

    public static void main(String[] args) {
        int[] vectorSizes = {10, 100, 100000};

        System.out.println("Количество элементов / Тест ");

        for (int N : vectorSizes) {
            System.out.println(Single_Test(N));
            for (int numThreads = 2; numThreads <= 10; numThreads++) {
                System.out.println(Multi_Test(N, numThreads));
            }
            System.out.println("--------------------------");
        }
    }
}