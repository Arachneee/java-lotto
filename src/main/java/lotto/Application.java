package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.*;

public class Application {
    static void printErr(Exception e){
        System.out.println("[ERROR] "+e.getMessage());
    }
    static Integer giveMoney(){
        int money = Integer.parseInt(Console.readLine());
        Integer lottoCount;

        if (money%1000 != 0)
            throw new IllegalArgumentException("구입금액은 1000으로 나누어 떨어지는 숫자여야 합니다.");

        lottoCount = money/1000;
        return lottoCount;
    }

    static ArrayList<Lotto> makeLotto(int lottoCount){
        System.out.printf("%n%d개를 구매했습니다.%n",lottoCount);

        ArrayList<Lotto> lottoList = new ArrayList<>();

        for(int i=0;i<lottoCount;i++){
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            System.out.println(numbers.toString());
            lottoList.add(new Lotto(numbers));
        }

        return lottoList;
    }

    static Integer[] makeDangchum(){
        System.out.printf("%n당첨 번호를 입력해 주세요%n");

        Integer[] rottoNumber = new Integer[6];
        StringTokenizer st = new StringTokenizer(Console.readLine(),",");

        if (st.countTokens() !=6) throw new IllegalArgumentException("6개의 번호를 입력해주세요.");

        for(int j=0;j<6;j++){
            String nextNumber = st.nextToken();
            int n = Integer.parseInt(nextNumber);
            if (n<0 || n>45) throw new IllegalArgumentException("로또 번호는 1부터 45 사이의 숫자여야 합니다.");
            rottoNumber[j] = Integer.parseInt(nextNumber);
        }
        return rottoNumber;
    }

    static int makeBonus(){
        System.out.printf("%n보너스 번호를 입력해 주세요.%n");
        int bonusNumber = Integer.parseInt(Console.readLine());

        if ((bonusNumber<0) || (bonusNumber>45)) throw new IllegalArgumentException("로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        return bonusNumber;
    }

    static HashMap<Integer,Integer> makeWinneramount(){
        HashMap<Integer,Integer> dangchumMap = new HashMap<>();
        dangchumMap.put(1,2000000000);
        dangchumMap.put(2,30000000);
        dangchumMap.put(3,1500000);
        dangchumMap.put(4,50000);
        dangchumMap.put(5,5000);
        return dangchumMap;
    }

    static double calrevenue(int lottoCount,int[] rankList,HashMap<Integer,Integer> dangchumMap){
        double totalMoney = 0;
        for(int i=0;i<rankList.length;i++){
            int count = rankList[i];
            totalMoney += Double.valueOf(dangchumMap.get(i+1))*count;
        }

        return Math.round((totalMoney/(lottoCount*1000))*100)/100.0;
    }

    static int[] makeRank(ArrayList<Lotto> lottoList,Integer[] rottoNumber,int bonusNumber){
        int[] rankList = new int[5];
        for(Lotto lotto:lottoList){
            int rank = lotto.rankCheck(rottoNumber,bonusNumber);
            if (rank <6) {
                rankList[rank-1] += 1;
            }
        }
        return rankList;
    }

    static void printStatus(int[] rankList, double revenue){
        System.out.println("당첨 통계\n"+
                "---\n" +
                "3개 일치 (5,000원) - "+rankList[4]+"개\n" +
                "4개 일치 (50,000원) - "+rankList[3]+"개\n" +
                "5개 일치 (1,500,000원) - "+rankList[2]+"개\n" +
                "5개 일치, 보너스 볼 일치 (30,000,000원) - "+rankList[1]+"개\n" +
                "6개 일치 (2,000,000,000원) - "+rankList[0]+"개\n" +
                "총 수익률은 "+revenue+"%입니다.");
    }

    public static void main(String[] args) {
        System.out.println("구입금액을 입력해 주세요.");

        try{
            int lottoCount = giveMoney();

            ArrayList<Lotto> lottoList = makeLotto(lottoCount);

            Integer[] rottoNumber = makeDangchum();

            int bonusNumber = makeBonus();

            HashMap<Integer,Integer> dangchumMap = makeWinneramount();

            int[] rankList = makeRank(lottoList,rottoNumber,bonusNumber);
            double revenue = calrevenue(lottoCount,rankList,dangchumMap);

            printStatus(rankList,revenue);
        } catch (IllegalArgumentException iae){
            printErr(iae);
        }
    }
}
