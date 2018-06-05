package com.meituan.hotel;


public class Leo {
    public static void main(String[] args) {
        int [] a = {9,3,8,4,7,1,5,6};
        sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }


    public static void sort(int [] a) {
        int i, j;
        for (i = 0; i < a.length; i++) {
            for (j = 1; j < a.length - i; j++) {
                if (a[j - 1] > a[j]) {
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }
}
