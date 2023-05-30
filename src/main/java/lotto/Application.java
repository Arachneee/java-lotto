package lotto;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.*;

public class Application {
    //에러메시지 출력
    private void printErr(Exception e){
        System.out.println("[ERROR] "+e.getMessage());
    }
    private Integer giveMoney(){
        String input = Console.readLine();

        //양의 정수만 입력받기
        if(!input.matches("^[1-9]\\d*$")) throw  new IllegalArgumentException("숫자를 입력하세요.");

        int money = Integer.parseInt(input);
        Integer lottoCount;

        //1000단위만 입력받기
        if (money%1000 != 0)
            throw new IllegalArgumentException("구입금액은 1000으로 나누어 떨어지는 숫자여야 합니다.");

        //로또 수량
        lottoCount = money/1000;
        return lottoCount;
    }

    //로또 생성
    private ArrayList<Lotto> makeLotto(int lottoCount){
        System.out.printf("%n%d개를 구매했습니다.%n",lottoCount);

        ArrayList<Lotto> lottoList = new ArrayList<>();

        for(int i=0;i<lottoCount;i++){
            //로또번호 랜덤 생성
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            System.out.println(numbers.toString());
            lottoList.add(new Lotto(numbers));
        }

        return lottoList;
    }

    //당첨번호 입력
    private Integer[] makeDangchum(){
        System.out.printf("%n당첨 번호를 입력해 주세요%n");

        Integer[] rottoNumber = new Integer[6];
        StringTokenizer st = new StringTokenizer(Console.readLine(),",");

        //6개만 받기
        if (st.countTokens() !=6) throw new IllegalArgumentException("6개의 번호를 입력해주세요.");

        for(int j=0;j<6;j++){
            String nextNumber = st.nextToken();
            int n = Integer.parseInt(nextNumber);

            // 1~45만 받기
            if (n<0 || n>45) throw new IllegalArgumentException("로또 번호는 1부터 45 사이의 숫자여야 합니다.");
            rottoNumber[j] = Integer.parseInt(nextNumber);
        }
        return rottoNumber;
    }


    //보너스 번호 받기
    private int makeBonus(){
        System.out.printf("%n보너스 번호를 입력해 주세요.%n");
        int bonusNumber = Integer.parseInt(Console.readLine());

        // 1~45만 받기
        if ((bonusNumber<0) || (bonusNumber>45)) throw new IllegalArgumentException("로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        return bonusNumber;
    }

    // 당첨 순위별 당첨 금액 맵핑
    private HashMap<Integer,Integer> makeWinneramount(){
        HashMap<Integer,Integer> dangchumMap = new HashMap<>();
        dangchumMap.put(1,2000000000);
        dangchumMap.put(2,30000000);
        dangchumMap.put(3,1500000);
        dangchumMap.put(4,50000);
        dangchumMap.put(5,5000);
        return dangchumMap;
    }

    // 수익률 계산
    private double calrevenue(int lottoCount,int[] rankList,HashMap<Integer,Integer> dangchumMap){
        double totalMoney = 0;
        for(int i=0;i<rankList.length;i++){
            int count = rankList[i];
            totalMoney += Double.valueOf(dangchumMap.get(i+1))*count;
        }

        return Math.round(((totalMoney/(lottoCount*1000))*100)*100)/100.0;
    }


    //로또 순위별 수량 계산
    private int[] makeRank(ArrayList<Lotto> lottoList,Integer[] rottoNumber,int bonusNumber){
        int[] rankList = new int[5];
        for(Lotto lotto:lottoList){
            //로또 순위 계산
            int rank = lotto.rankCheck(rottoNumber,bonusNumber);
            if (rank <6) {
                // index 0부터 1등
                rankList[rank-1] += 1;
            }
        }
        return rankList;
    }

    //통계값 출력
    private void printStatus(int[] rankList, double revenue){
        System.out.println("당첨 통계\n"+
                "---\n" +
                "3개 일치 (5,000원) - "+rankList[4]+"개\n" +
                "4개 일치 (50,000원) - "+rankList[3]+"개\n" +
                "5개 일치 (1,500,000원) - "+rankList[2]+"개\n" +
                "5개 일치, 보너스 볼 일치 (30,000,000원) - "+rankList[1]+"개\n" +
                "6개 일치 (2,000,000,000원) - "+rankList[0]+"개\n" +
                "총 수익률은 "+revenue+"%입니다.");
    }
    public void run(){
        System.out.println("구입금액을 입력해 주세요.");
        try{
            //투입금액을 받아 금액에 맞게 로또를 사는 기능
            int lottoCount = giveMoney();

            //로또를 수량에 맞게 만드는 기능
            ArrayList<Lotto> lottoList = makeLotto(lottoCount);

            //당첨번호를 입력받는 기능
            Integer[] rottoNumber = makeDangchum();

            //보너스번호 입력받는 기능
            int bonusNumber = makeBonus();

            //당첨등수별 당첨 금액 맵핑 기능
            HashMap<Integer,Integer> dangchumMap = makeWinneramount();

            //로또의 순위 매기는 기능
            int[] rankList = makeRank(lottoList,rottoNumber,bonusNumber);

            //수익률 계산기능
            double revenue = calrevenue(lottoCount,rankList,dangchumMap);

            //통계치 출력 기능
            printStatus(rankList,revenue);
        } catch (IllegalArgumentException iae){
            //에러 메시지 출력
            printErr(iae);
        }
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
