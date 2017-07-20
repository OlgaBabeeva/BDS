import java.util.*;

public class MontyHall {

    private static Random r = new Random();

    public static void main(String[] args) {
        MontyHall montyHall = new MontyHall();
        montyHall.model(times(args));
    }

    private void model(int N) {

        int neverChangeWinsCount = 0;
        int alwaysChangeWinsCount = 0;

        for (int i = 0; i < N; i++) {

            Game game = new Game();

            neverChangeWinsCount += neverChange(game);
            alwaysChangeWinsCount += alwaysChange(game);
        }

        System.out.printf("'never change' wins with %s probability%n", (double) neverChangeWinsCount / N);
        System.out.printf("'always change' wins with %s probability%n", (double) alwaysChangeWinsCount / N);
    }

    private Integer neverChange(Game game) {
        Integer choice = r.nextInt(3);
        return game.cups.get(choice);
    }

    private Integer alwaysChange(Game game) {
        Integer choice = r.nextInt(3);
        Integer newChoice = newChoice(game, choice);
        return game.cups.get(newChoice);
    }

    private Integer newChoice(Game game, Integer choice) {

        if (choice.equals(game.coinIndex)) {
            return game.emptyIndexes.get(r.nextInt(2));
        } else {
            //this is the trickiest moment of the paradox in my opinion:
            //if we originally have chosen empty cup, when we switch we always get the coin
            return game.coinIndex;
        }
    }

    class Game {

        public List<Integer> cups = new ArrayList<>(Collections.nCopies(3, 0));
        public Integer coinIndex;
        public List<Integer> emptyIndexes = new ArrayList<>(Arrays.asList(0, 1, 2));

        public Game() {
            coinIndex = r.nextInt(3);
            cups.set(coinIndex, 1);
            emptyIndexes.remove(coinIndex);
        }
    }

    private static int times(String[] args) {
        return args.length > 0? Integer.valueOf(args[0]) : 1000;
    }
}
