import java.util.Scanner;

public class Main {

    private static int depthCutoff(Scanner scanner) {
        int depthCutOff = 0;
        while (depthCutOff <= 0) {
            System.out.println("Input a depth cutoff: ");
            depthCutOff = scanner.nextInt();
        }
        return depthCutOff;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int depthCutOff = depthCutoff(scanner);
        System.out.println(depthCutOff);

        Runner start = new Runner(depthCutOff);
        start.run();

        scanner.close();
        System.out.println("End of Program!");
    }
}
