import java.io.File;
import java.util.*;

public class my_poker_solution {
    enum Rank {
        TWO('2',2),
        THREE('3',3),
        FOUR('4',4),
        FIVE('5',5),
        SIX('6',6),
        SEVEN('7',7),
        EIGHT('8',8),
        NNE('9',9),
        TEN('T',10),
        JACK('J',11),
        QUEEN('Q',12),
        KING('K',13),
        ACE('A',14);

        private char value;
        private int number;

        Rank(char v, int n) {
            value = v;
            number = n;
        }
        public char getValue(){
            return value;
        }

        public int getNumber() {
            return number;
        }
    }

    enum Suit {
        DIAMOND('D'),
        HEARTS('H'),
        SPADES('S'),
        CLUBS('C');

        private char value;

        Suit(char v) {
            value = v;
        }
        public char getValue() {
            return value;
        }
    }

    static class Score {
        private int score_p1;
        private int score_p2;

        Score(int s1, int s2) {
            score_p1 = s1;
            score_p2 = s2;
        }

        public int getScore_p1() {
            return score_p1;
        }

        public void setScore_p1(int score_p1) {
            this.score_p1 = score_p1;
        }

        public int getScore_p2() {
            return score_p2;
        }

        public void setScore_p2(int score_p2) {
            this.score_p2 = score_p2;
        }
    }

    static class card {
        private Rank rank;
        private Suit suit;

       card(Rank r, Suit s) {
           rank = r;
           suit = s;
       }

        public Rank getRank() {
            return rank;
        }

        public void setRank(Rank rank) {
            this.rank = rank;
        }

        public Suit getSuit() {
            return suit;
        }

        public void setSuit(Suit suit) {
            this.suit = suit;
        }
    }

    public static Boolean allSameSuit(List<card> list) {
        if(list.get(4).getRank().getNumber() - list.get(0).getRank().getNumber() == 4){
            return true;
        }
        return false;
    }

    public static Boolean isConsecutive(List<card> list) {
        if(list.get(0).getSuit().equals(list.get(1).getSuit()) &&
                list.get(1).getSuit().equals(list.get(2).getSuit()) &&
                list.get(2).getSuit().equals(list.get(3).getSuit()) &&
                list.get(3).getSuit().equals(list.get(4).getSuit())){
            return true;
        }
        return false;
    }

    public static int highCard(List<card> list, HashMap<Character, Integer> countMap, Map<Character, Rank> charToRank) {
        int highestRank = 0;
        for(Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() == 1) {
                int tmp = charToRank.get(entry.getKey()).getNumber();
                if(tmp>highestRank)
                    highestRank = tmp;
            }
        }

        return highestRank;
    }

    public static int pair(HashMap<Character, Integer> countMap, Map<Character, Rank> charToRank) {
        int pair = 0, pairRank = 0;
        for(Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() == 2) {
                pair++;
                pairRank = charToRank.get(entry.getKey()).getNumber();
            }
        }

        if (pair == 1)
            return pairRank;
        return 0;
    }

    public static int twoPairs(HashMap<Character, Integer> countMap, Map<Character, Rank> charToRank) {
        int pair = 0, pairHighestRank = 0;
        for(Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() == 2) {
                pair++;
                int tmpNumber = charToRank.get(entry.getKey()).getNumber();
                if(tmpNumber>pairHighestRank)
                    pairHighestRank = tmpNumber;
            }
        }

        if (pair == 2)
            return pairHighestRank;
        return 0;
    }

    public static int threeOfAKind(HashMap<Character, Integer> countMap, Map<Character, Rank> charToRank) {
        for(Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() == 3)
                return charToRank.get(entry.getKey()).getNumber();

        }
        return 0;
    }

    public static int straight(List<card> list) {
        if(isConsecutive(list))
            return list.get(4).getRank().getNumber();
        return 0;
    }

    public static  int flush (List<card> list) {
        if(allSameSuit(list)) {
            int highestRank = 0;
            for(card c: list) {
                int tmpNumber = c.getRank().getNumber();
                if(tmpNumber > highestRank)
                    highestRank = tmpNumber;
            }
            return highestRank;
        }
        return 0;
    }

    public static int fullHouse(HashMap<Character, Integer> countMap, Map<Character, Rank> charToRank) {
        int threeOfKind = 0, pair = 0, threeOfKindRank = 0;
        for(Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() > 2)
                pair++;
            if(entry.getValue() == 3) {
                threeOfKind++;
                threeOfKindRank = charToRank.get(entry.getKey()).getNumber();
            }
        }

        if(threeOfKind == 1 && pair == 2)
            return threeOfKindRank;
        return 0;
    }

    public static int fourOfAKind(HashMap<Character, Integer> countMap, Map<Character, Rank> charToRank) {
        for(Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() >= 4)
                return charToRank.get(entry.getKey()).getNumber();
        }

        return 0;
    }


    public static int straightFlush(List<card> list) {
        if(isConsecutive(list) && allSameSuit(list) && list.get(0).getRank().getValue() != 'T')
            return list.get(4).getRank().getNumber(); // the highest ranking card of straight flush
        return 0;
    }

    public static int royalFlush(List<card> list) {
        if(isConsecutive(list) && allSameSuit(list) && list.get(0).getRank().getValue() == 'T')
           return 14; // Ace is the highest ranking card of royal flush
        return 0;
    }

    public static int getWinner(List<card> list, HashMap<Character, Integer> countMap,
                                Map<Character, Rank> charToRank, int combination) {
        //return the possible winner for each combination
        if(combination == 0)
            return royalFlush(list);
        else if(combination==1)
            return straightFlush(list);
        else if (combination==2)
            return fourOfAKind(countMap, charToRank);
        else if(combination==3)
            return fullHouse(countMap, charToRank);
        else if(combination==4)
            return flush(list);
        else if(combination==5)
            return straight(list);
        else if(combination==6)
            return threeOfAKind(countMap, charToRank);
        else if(combination==7)
            return twoPairs(countMap, charToRank);
        else if(combination==8)
            return pair(countMap, charToRank);
        else
            return highCard(list, countMap, charToRank);
    }

    public static void updateLists(String line, Map<Character, Rank> charToRank, Map<Character, Suit> charToSuit, List<card> list_p1, List<card> list_p2) {
        String[] array = line.split(" ");
        int seperator = 0;


        PriorityQueue<card> queue_p1 = new PriorityQueue<>(new Comparator<card>() {
            @Override
            public int compare(card c1, card c2) {
                return c1.getRank().compareTo(c2.getRank());
            }

        });

        PriorityQueue<card> queue_p2 = new PriorityQueue<>(new Comparator<card>() {
            @Override
            public int compare(card c1, card c2) {
                return c1.getRank().compareTo(c2.getRank());
            }

        });

        for (String s : array) {
            if (seperator < 5)
                queue_p1.add(new card(charToRank.get(s.charAt(0)), charToSuit.get(s.charAt(1))));
            else
                queue_p2.add(new card(charToRank.get(s.charAt(0)), charToSuit.get(s.charAt(1))));
            seperator++;
        }

        while (queue_p1.peek() != null) {
            list_p1.add(queue_p1.poll());
        }

        while (queue_p2.peek() != null) {
            list_p2.add(queue_p2.poll());
        }
    }

    public static void updateHands(List<card> list_p1, List<card> list_p2,
                                   HashMap<Character, Integer> countMap_p1, HashMap<Character, Integer> countMap_p2,
                                   Map<Character, Rank> charToRank, Score score) {
        int winner_p1, winner_p2;

        for(int i=0; i<10;i++) {
            winner_p1 = getWinner(list_p1, countMap_p1, charToRank, i);
            winner_p2 = getWinner(list_p2, countMap_p2, charToRank, i);
//            System.out.println("player 1 winner: "+i+" winner result: "+winner_p1);
//            System.out.println("player 2 winner: "+i+" winner result: "+winner_p2);
            if(winner_p1>winner_p2) {
                score.setScore_p1(score.getScore_p1()+1);
                break;
            } else if(winner_p1<winner_p2) {
                score.setScore_p2(score.getScore_p2()+1);
                break;
            }
        }
    }


    public static void updateCountMap(List<card> list, HashMap<Character, Integer> countMap) {
        for(card c : list) {
            char tmpValue = c.getRank().getValue();
            if(countMap.containsKey(tmpValue)) {
                countMap.put(tmpValue, countMap.get(c.getRank().getValue()).intValue()+1);
            } else {
                countMap.put(tmpValue, 1);
            }
        }
    }

    public static void main (String[] args) {
        final Map<Character, Rank> charToRank = new HashMap<>();
        for(Rank r : Rank.values()) {
            charToRank.put(r.getValue(), r);
        }

        final Map<Character, Suit> charToSuit = new HashMap<>();
        for(Suit s : Suit.values()) {
            charToSuit.put(s.getValue(), s);
        }

        Score score = new Score(0,0);//score instance records winning hands of each player

        try {
            Scanner sc = new Scanner(new File("poker-hands.txt"));
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                List<card> list_p1 = new ArrayList<>(); // list of cards in ascending order
                List<card> list_p2 = new ArrayList<>();
                // record how many cards per each rank each player has
                HashMap<Character,Integer> countMap_p1 = new HashMap<>();
                HashMap<Character,Integer> countMap_p2 = new HashMap<>();

                updateLists(line, charToRank, charToSuit, list_p1, list_p2);
                updateCountMap(list_p1, countMap_p1);
                updateCountMap(list_p2, countMap_p2);
                updateHands(list_p1, list_p2, countMap_p1, countMap_p2, charToRank, score);
            }

            System.out.println("Player 1: "+score.getScore_p1());
            System.out.println("Player 2: "+score.getScore_p2());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}