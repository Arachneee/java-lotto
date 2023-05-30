##기능단위
* Integer giveMoney() : 투입금액을 받아 금액에 맞게 로또를 사는 기능
* ArrayList<Lotto> makeLotto(int lottoCount) : 로또를 수량에 맞게 만드는 기능
* Integer[] makeDangchum() : 당첨번호를 입력받는 기능
* int makeBonus() : 보너스번호 입력받는 기능
* HashMap<Integer,Integer> makeWinneramount() : 당첨등수별 당첨 금액 맵핑 기능
* int[] makeRank(ArrayList<Lotto> lottoList,Integer[] rottoNumber,int bonusNumber) : 로또의 순위 매기는 기능
* double calrevenue(int lottoCount,int[] rankList,HashMap<Integer,Integer> dangchumMap) : 수익률 계산기능
* printStatus(int[] rankList, double revenue) : 통계치 출력 기능
