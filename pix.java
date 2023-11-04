import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
public class pix 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path to the image file: ");
        String imagePath = scanner.nextLine();

        try 
        {
            BufferedImage image = ImageIO.read(new File(imagePath));
            while (true) 
            {
                System.out.println("Options:");
                System.out.println("1. Grayscale");
                System.out.println("2. Invert Colors");
                System.out.println("3. Posterise");
                System.out.println("4. Sepia");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                switch (choice) 
                {
                    case 1:
                        save(applyGrayscaleFilter(image),"grayimage.jpg");
                        break;
                    case 2:
                        save(applyInvertColorsFilter(image),"invertcoloredimg.jpg");
                        break;
                    case 3:
                        System.out.print("Enter Posterisation Level: ");
                        int posterisationLevel = scanner.nextInt();
                        save(applyPosterFilter(image, posterisationLevel),"posterizedImage.png");
                        break;
                    case 4:
                        save(applySepiaFilter(image), "sepiaimage.jpg");
                        break;
                    case 5:
                        exit();
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                        break;
                }
            }
        } 
        catch (IOException e)
        {
            System.err.println("Error loading the image: " + e.getMessage());
        }
    }

    private static BufferedImage applyGrayscaleFilter(BufferedImage image) 
    {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage GrayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height; y++) 
            {
                Color color = new Color(image.getRGB(x, y));
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color grayscale = new Color(avg, avg, avg);
                image.setRGB(x, y, grayscale.getRGB());
            }
        }
        System.out.println("Grayscale filter applied.");
        return GrayscaleImage;
    }

    private static BufferedImage applyInvertColorsFilter(BufferedImage image) 
    {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage invertColoredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height; y++) 
            {
                Color color = new Color(image.getRGB(x, y));
                Color inverted = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
                image.setRGB(x, y, inverted.getRGB());
            }
        }
        System.out.println("Invert Colors filter applied.");
        return invertColoredImage;
    }


    public static BufferedImage applyPosterFilter(BufferedImage image, int posterizationLevel) 
    {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage posterizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int levels = posterizationLevel;

        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height; y++) 
            {
                Color color = new Color(image.getRGB(x, y));
                int red = Math.round(color.getRed() * levels / 256) * 256 / levels;
                int green = Math.round(color.getGreen() * levels / 256) * 256 / levels;
                int blue = Math.round(color.getBlue() * levels / 256) * 256 / levels;

                Color posterColor = new Color(red, green, blue);
                posterizedImage.setRGB(x, y, posterColor.getRGB());
            }
        }

        return posterizedImage;
    }


    private static BufferedImage applySepiaFilter(BufferedImage image) 
    {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y));

                int red = (int) (color.getRed() * 0.393 + color.getGreen() * 0.769 + color.getBlue() * 0.189);
                int green = (int) (color.getRed() * 0.349 + color.getGreen() * 0.686 + color.getBlue() * 0.168);
                int blue = (int) (color.getRed() * 0.272 + color.getGreen() * 0.534 + color.getBlue() * 0.131);

                red = Math.min(255, red);
                green = Math.min(255, green);
                blue = Math.min(255, blue);

                Color sepiaColor = new Color(red, green, blue);
                sepiaImage.setRGB(x, y, sepiaColor.getRGB());
            }
        }

        return sepiaImage;
    }
    private static void save(BufferedImage image, String imagePath) 
    {
        try 
        {
            File outputImageFile = new File(imagePath);
            ImageIO.write(image, "png", outputImageFile);
            System.out.println("Image saved.");
        } 
        catch (IOException e) 
        {
            System.err.println("Error saving the image: " + e.getMessage());
        }
    }
    private static void exit()
    {
        System.exit(0);
    }
}
