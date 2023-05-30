package lotto;

import java.util.*;
import java.util.stream.*;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
        if (numbers.stream().distinct().count() != numbers.size()){
            throw new IllegalArgumentException();
        }
    }

    //로또 순위 계산
    public int rankCheck(Integer[] rottoNumber, int bonusNumber){
        int rank = 8;

        //숫자 하나받을 때마다 순위값 1씩 줄임
        for(Integer num:numbers){
            if (Arrays.<Integer>asList(rottoNumber).contains(num)){
                rank--;
            }
        }

        //6개 다 맞으면 1등으로 바꿈
        if (rank==2) return --rank;

        //3등은 보너스 점수맞으면 2등으로 승격
        if (rank==3 && Arrays.asList(rottoNumber).contains(bonusNumber)) return --rank;

        return rank;
    }
}
