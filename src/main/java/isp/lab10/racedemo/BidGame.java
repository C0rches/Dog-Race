package isp.lab10.racedemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MyActionListener implements ActionListener{
    Button b = null;
    public MyActionListener(Button b){  //constructor for MyActionListener takes a freference to a button;
        this.b = b; //stores the passed in reference to the button object in an instance variable so it can be accessed any time;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
public class BidGame extends Thread {
    private static JFrame frame;
    private static JLabel moneyLabel;
    private static JButton bidButton;
    private static DogPanel[] dogPanels;

    public int joc = 1;

    public void run() {
        try {
            startGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void startGame() throws InterruptedException {

        frame = new JFrame("Dog Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Set up the top panel to display the player's money
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel moneyTextLabel = new JLabel("Money:");
        moneyLabel = new JLabel("$100");
        topPanel.add(moneyTextLabel);
        topPanel.add(moneyLabel);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        // Set up the panel to display the dogs
        JPanel dogPanel = new JPanel(new GridLayout(1, 4));
        dogPanels = new DogPanel[4];
        dogPanels[0] = new DogPanel("German Shepard", "Ciobanesc.png");
        dogPanels[1] = new DogPanel("Husky", "Husky.png");
        dogPanels[2] = new DogPanel("Pitbull", "Pitbull.png");
        dogPanels[3] = new DogPanel("Labrador", "Labrador.png");
        for (int i = 0; i < 4; i++) {
            dogPanel.add(dogPanels[i]);
        }
        frame.getContentPane().add(dogPanel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "The race will start now");

                joc=0;
                System.out.println("apasat exit");
                synchronized (frame){
                frame.notify();}

            }
        });

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(exitButton);
        frame.getContentPane().add(leftPanel, BorderLayout.WEST);

        // Set up the button to place a bet
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bidButton = new JButton("Place Bet");

        bidButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Place bet logic here
                JOptionPane.showMessageDialog(frame, "Choose the dog!");

            }
        });
        bottomPanel.add(bidButton);
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Display the main frame
        frame.pack();
        frame.setSize(600, 300);
        frame.setVisible(true);


        // Launch the second frame if the user wants to play again

        System.out.println("inainte de wait");

        synchronized (frame){
        frame.wait();
        }
        System.out.println("dupa wait");

    }

}
    class DogPanel extends JPanel {
        private String name;
        private ImageIcon icon;

        public DogPanel(String name, String iconFilename) {
            this.name = name;
            ImageIcon originalIcon = new ImageIcon(iconFilename);
            Image scaledImage = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            this.icon = new ImageIcon(scaledImage);

            // Add a mouse listener to the panel
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    // Dog panel clicked logic here
                    JOptionPane.showMessageDialog(null, "You want to bid on " + name);

                }
            });
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the dog icon and name
            icon.paintIcon(this, g, 0, 0);
            g.drawString(name, 5, icon.getIconHeight() + 5);
        }
    }

