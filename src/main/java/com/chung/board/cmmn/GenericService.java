package com.chung.board.cmmn;

import lombok.ToString;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenericService {
    private int SIZE_CHROMOSOME = 5;
    private int DATA_SIZE = 6;
    private int GENERATION_COUNT = 100;
    private point[] pointArr = new point[DATA_SIZE];
    private double[][] distance = new double[DATA_SIZE][DATA_SIZE];
    private List<int[]> generationList = new ArrayList<>();
    private List<double[]> reportList = new ArrayList<>();

    private int[] resultRoute = new int[SIZE_CHROMOSOME];
    private double resultScore = 0;
    private int MAX_LOOP = 5000;

    @ToString
    public class point {
        public point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;
    }

    public String getShortestRoot() {
        initGeneration();
        while (true){
            double[] result=calculateGoodnessOfFit();
            if (!bContinue(result, reportList.size())) {
                List<int[]> newList = getNextGeneration(result);
                generationList.clear();
                generationList.addAll(newList);
            }else {
                break;
            }
        }

        String temp = Arrays.toString(resultRoute);
        temp = temp.replace('1', 'B').replace('2', 'C').replace('3', 'D').replace('4', 'E').replace('5', 'F');
        System.out.println("현재까지의 최적의 적합도 = " + resultScore);
        System.out.println("현재까지의 최적의 루트 = " + Arrays.toString(resultRoute));
        System.out.println("최종 경로: A-" + temp + "-A/ 적합도: " + resultScore);
        return "최종 경로: A-" + temp + "-A/ 적합도: " + resultScore;
    }


    //0) 거리정보 테이블.
    public GenericService() {
        pointArr[0] = new point(1, 24);
        pointArr[1] = new point(32, 53);
        pointArr[2] = new point(14, 5);
        pointArr[3] = new point(86, 2);
        pointArr[4] = new point(214, 342);
        pointArr[5] = new point(100, 30);

        for (int i = 0; i < DATA_SIZE; i++) {
            for (int j = 0; j < DATA_SIZE; j++) {
                distance[i][j] = distance(pointArr[i].x, pointArr[i].y, pointArr[j].x, pointArr[j].y, "meter");
            }
        }
//        System.out.println("거리정보 테이블: " + Arrays.toString(distance));
    }


    //1) 초기 염색체 생성+세대 생성.
    private List<int[]> initGeneration() {
        for (int i = 0; i < GENERATION_COUNT; i++) {
            generationList.add(getRandomChromosome());
        }
        return generationList;
    }

    private int[] getRandomChromosome() {
        Random rand = new Random();
        int[] chromosome = new int[SIZE_CHROMOSOME];

        for (int i = 0; i < SIZE_CHROMOSOME; i++) {
            chromosome[i] = rand.nextInt(SIZE_CHROMOSOME) + 1;    //1~CHROME_SIZE 이내의 값 출력.
            boolean duplicate = false;
            for (int j = 0; j < i; j++) {
                if (chromosome[i] == chromosome[j]) {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                i--;
                continue;
            }
        }
        return chromosome;
    }

    //2) 적합도 계산.
    private double[] calculateGoodnessOfFit() {
        double[] report = new double[GENERATION_COUNT];
        for (int i = 0; i < generationList.size(); i++) {
            report[i] = calChromeFit(generationList.get(i));
        }
        reportList.add(report);
        return report;
    }

    //3) 적합도 기준으로 선택하기
    private List<int[]> getNextGeneration(double[] report) {
        List<int[]> nextGeneration = new ArrayList<>();
        double[] roulette = getRouletteWheel(report);
        int[] child = null;

        Random random = new Random();
        //대상이 되는 유전자.
        for (int i = 0; i < generationList.size(); i++) {
            int[] man = generationList.get(i);
            int[] girl = null;
            while (true) {

                double standard = 0;
                double randomValue = random.nextDouble();
                //교배할 종 선택.
                for (int j = 0; j < roulette.length; j++) {
                    standard += roulette[j];
                    if (randomValue < standard) {
                        girl = generationList.get(j);
                        break;
                    }
                }

                //교배후 자식 생성.
                assert girl != null;
                child = getChildChromeFromParent(man, girl);
                //중복검사.
                String temp = Arrays.toString(child);
                boolean duplicate = false;
                for (int[] arr : nextGeneration) {
                    if (Arrays.toString(arr).equals(temp)) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    break;
                }
            }

            nextGeneration.add(child);
        }
        return nextGeneration;
    }


    //**********************************************************
    //두 점 사이의 거리.
    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if (unit == "meter") {
            dist = dist * 1609.344;
        }

        return (dist);
    }

    // This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    //적합도 계산.
    private double calChromeFit(int[] arr) {
        double down = distance[0][arr[0]] + distance[arr[arr.length - 1]][4];    //처음과 끝점.
        for (int i = 0; i < arr.length - 1; i++) {
            down += distance[i][i + 1];
        }

        double temp = (1000 / down);
        return temp * temp;
    }

    //종료조건 판별.
    private boolean bContinue(double[] report, int size) {
        if (resultScore == 0) {
            resultScore = report[0];
            resultRoute = generationList.get(0);
        }

        System.out.println("*******회차정보: " + size + "**************************");
        for (int i = 0; i < generationList.size(); i++) {
            if (report[i] < resultScore) {
                resultScore = report[i];
                resultRoute = generationList.get(i);
            }
//            System.out.println("이번회차 경로: " + Arrays.toString(generationList.get(i)) + "/적합도 결과: " + report[i]);
        }

//        System.out.println("현재까지의 최적의 적합도 = " + resultScore);
//        System.out.println("현재까지의 최적의 루트 = " + Arrays.toString(resultRoute));

        if (size >= MAX_LOOP) {
            return true;
        }
        return false;
    }

    //룰렛 휠 만들기.
    private double[] getRouletteWheel(double[] report) {
        double down = 0;
        for (double d : report) {
            down += d;
        }

        double[] roulette = new double[report.length];
        for (int i = 0; i < report.length; i++) {
            roulette[i] = report[i] / down;
        }
        return roulette;
    }

    //교배.
    private int[] getChildChromeFromParent(int[] man, int[] girl) {
//        System.out.println("아버지 정보: " + Arrays.toString(man));
//        System.out.println("어머니 정보: " + Arrays.toString(girl));

        //자식 정보 초기화.
        Random random = new Random();
        int temp = 0;
        int[] child = new int[man.length];
        Arrays.fill(child, -1);

        int divisionPoint = random.nextInt(man.length);
        for (int i = 0; i < child.length; i++) {
            if (divisionPoint < i) {
                child[i] = getChromeInfo(man, child, i);
            } else {
                child[i] = getChromeInfo(girl, child, i);
            }
        }

        //돌연변이=>0.1%로 돌연변이 코드.
        if (random.nextInt(100) + 1 <= 1) {
            temp = child[1];
            child[1] = child[3];
            child[3] = temp;
        }

//        System.out.println("자식 정보: " + Arrays.toString(child));
        return child;
    }

    //childIdx 자리의 수를 반환하되 이전 값과 중복되면 그 다음 idx,
    private int getChromeInfo(int[] parent, int[] child, int childIdx) {
        boolean duplicate = false;
        int valueIdx = childIdx;
        int temp = 0;
        while (true) {
            temp = parent[valueIdx];

            duplicate = false;
            for (int i = 0; i < childIdx; i++) {
                if (child[i] != -1 && child[i] == temp) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                break;
            }

            valueIdx++;
            if (valueIdx > parent.length - 1) {
                valueIdx = 0;
            }
        }
        return temp;
    }
}
