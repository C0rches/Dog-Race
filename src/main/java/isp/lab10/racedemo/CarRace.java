package isp.lab10.racedemo;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CarRace {
    public static void main(String[] args) throws InterruptedException {

        BidGame game = new BidGame();


            game.start();
            game.join();

            if(game.joc==0) {
                JFrame frame = new JFrame("Dog Race");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                CarRace.CarPanel carPanel = new CarRace.CarPanel();

                frame.getContentPane().add(carPanel);
                frame.pack();
                frame.setSize(500, 500);
                frame.setVisible(true);

                CarRace.Car car1 = new CarRace.Car("Ciobanesc", carPanel);
                CarRace.Car car2 = new CarRace.Car("Husky", carPanel);
                CarRace.Car car3 = new CarRace.Car("Pitbull", carPanel);
                CarRace.Car car4 = new CarRace.Car("Labrador", carPanel);

                JFrame frame2 = new JFrame("Semaphore");
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                SemaphorePanel semaphorePanel = new SemaphorePanel();

                frame2.getContentPane().add(semaphorePanel);
                frame2.pack();
                frame2.setVisible(true);
                PlaySound ps = new PlaySound();
                long timp1, timp2;
                Timer ceas = new Timer();
                SemaphoreThread semaphoreThread = new SemaphoreThread(semaphorePanel);

                semaphoreThread.start();
                semaphoreThread.join();

                // Start the cars
                ps.playSound();
                timp1 = System.currentTimeMillis();
                ceas.start();

                car1.start();
                car2.start();
                car3.start();
                car4.start();
                car1.join();
                car2.join();
                car3.join();
                car4.join();
                timp2 = System.currentTimeMillis();
                ps.stopSound();

                double timp = timp2 - timp1;
                System.out.println("The running time is: " + ceas.time + " seconds!!!!");


            }
}

static class Car extends Thread {
    private String name;
    private int distance = 0;
    private CarPanel carPanel;


    public Car(String name, CarPanel carPanel) {
        //set thread name;
        setName(name);
        this.name = name;
        this.carPanel = carPanel;
    }

    public void run() {
        Global.startTime = System.currentTimeMillis();
        while (distance < 400) {

            // simulate the car moving at a random speed
            int speed = (int) (Math.random() * 10) + 1;
            distance += speed;

            carPanel.updateCarPosition(name, distance);

            try {
                // pause for a moment to simulate the passage of time
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        Global.endTime = System.currentTimeMillis();
        carPanel.carFinished(name);
    }
}

static class CarPanel extends JPanel {
    private int[] carPositions;
    private String[] carNames;
    private Color[] carColors;
    private BufferedImage[] carImages;


    public CarPanel() {
        carPositions = new int[4];
        carNames = new String[]{"Ciobanesc", "Husky", "Pitbull", "Labrador"};
        carColors = new Color[]{Color.BLUE, Color.GREEN, Color.YELLOW};
        carImages = new BufferedImage[4];
        try {
            // Load the car images from file
            carImages[0] = ImageIO.read(new File(".\\Ciobanesc.png"));
            carImages[1] = ImageIO.read(new File(".\\Husky.png"));
            carImages[2] = ImageIO.read(new File(".\\Pitbull.png"));
            carImages[3] = ImageIO.read(new File(".\\Labrador.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 4; i++) {
            int yPos = 50 + i * 100; // Vertical position of the car
            int xPos = carPositions[i]; // Horizontal position of the car
            int carWidth = 100; // Width of the car image
            int carHeight = 70; // Height of the car image

            g.drawImage(carImages[i], xPos, yPos, carWidth, carHeight, null);
            g.setColor(Color.BLACK);
            g.drawString(carNames[i], xPos, yPos - 5);
        }
    }

    public void updateCarPosition(String carName, int distance) {
        int carIndex = getCarIndex(carName);
        if (carIndex != -1) {
            carPositions[carIndex] = distance;
            repaint();
        }
    }

    public void carFinished(String carName) {

         double timp;
         timp = Global.endTime - Global.startTime;

        System.out.println(carName +" finished race in " + timp/1000 + " seconds");
    }

    private int getCarIndex(String carName) {
        for (int i = 0; i < 4; i++) {
            if (carNames[i].equals(carName)) {
                return i;
            }
        }
        return -1;
    }
}}
