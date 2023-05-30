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
    }
    public int rankCheck(Integer[] rottoNumber, int bonusNumber){
        int rank = 8;

        for(Integer num:numbers){
            if (Arrays.<Integer>asList(rottoNumber).contains(num)){
                rank--;
            }
        }

        if (rank==2) return --rank;

        if (rank==3 && Arrays.asList(rottoNumber).contains(bonusNumber)) return --rank;

        return rank;
    }

    // TODO: 추가 기능 구현
}
