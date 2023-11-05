/*
Devashish Singh Rajput
Programming Practices Mini project
 */
import java.awt.Color;//this class allow to create new color
import java.awt.image.BufferedImage;//to store image data
import java.io.File;//to read and write input and output file respectively
import java.io.IOException;//to handle various exceptions
import java.util.Scanner;//to take inputs from the user
import javax.imageio.ImageIO;
public class pix 
{
    public static void main(String[] args) 
    {
        //taking file name as input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the image filename in images folder: ");
        String imageName = scanner.nextLine();
        
        //handling exception arising during reading image fom file 
        try 
        { 
            //saving data of given image in buffered image
            BufferedImage image = ImageIO.read(new File("images/"+imageName));
            
            //infinite loop to continuosly prompt user for options
            while (true) 
            {
                //displaying options
                System.out.println("Select the mood:");
                System.out.println("1. Sad");
                System.out.println("2. Surprised");
                System.out.println("3. Blank");
                System.out.println("4. Serious");
                System.out.println("5. Blind");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                //switch case to react according to selected options
                switch (choice) 
                {
                    case 1:
                        save(applyGrayscaleFilter(image),"sad"+imageName);
                        break;
                    case 2:
                        save(applyInvertColorsFilter(image),"surprised"+imageName);
                        break;
                    case 3:
                        //prompting user for blankness level
                        System.out.print("Enter blankness Level: ");
                        
                        int posterisationLevel = scanner.nextInt();
                        
                        save(applyPosterFilter(image, posterisationLevel),"blank"+posterisationLevel+imageName);
                        break;
                    case 4:
                        save(applySepiaFilter(image), "serious"+imageName);
                        break;
                    case 5:
                        save(image,"blind"+imageName);
                        break;
                    case 6:
                        scanner.close();
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
            //displaying error messages
            System.err.println("Error loading the image: " + e.getMessage());
        }
        scanner.close();
    }

    //apply filter on image according to sad mood
    private static BufferedImage applyGrayscaleFilter(BufferedImage image) 
    {
        int width = image.getWidth();
        int height = image.getHeight();
        
        //creating a temporary image file so that original file in not affected
        BufferedImage GrayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height; y++) 
            {
                //iterates through each pixel
                //creates new color to store gray value  
                Color color = new Color(image.getRGB(x, y));

                //calculating gray color values 
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color grayscale = new Color(avg, avg, avg);
                
                //apply gray color to copy of image and returns it
                GrayscaleImage.setRGB(x, y, grayscale.getRGB());
            }
        }
        //output message
        System.out.println("Grayscale filter applied.");
        return GrayscaleImage;
    }

    //method to apply filter for surprised mood
    private static BufferedImage applyInvertColorsFilter(BufferedImage image) 
    {
        int width = image.getWidth();
        int height = image.getHeight();
        
        //creates a copy of image 
        BufferedImage invertColoredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height; y++) 
            {
                Color color = new Color(image.getRGB(x, y));

                Color inverted = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
                invertColoredImage.setRGB(x, y, inverted.getRGB());
            }
        }
        
        //output message
        System.out.println("Invert Colors filter applied.");
        return invertColoredImage;
    }

    //filter for lost mood
    private static BufferedImage applyPosterFilter(BufferedImage image, int posterizationLevel) 
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

                //algorithm to create effect
                int red = Math.round(color.getRed() * levels / 256) * 256 / levels;
                int green = Math.round(color.getGreen() * levels / 256) * 256 / levels;
                int blue = Math.round(color.getBlue() * levels / 256) * 256 / levels;

                //applying new color to bufferimage
                Color posterColor = new Color(red, green, blue);
                posterizedImage.setRGB(x, y, posterColor.getRGB());
            }
        }

        return posterizedImage;
    }

    //filter for serious mood
    private static BufferedImage applySepiaFilter(BufferedImage image) 
    {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height; y++) 
            {
                Color color = new Color(image.getRGB(x, y));

                //algorithm to create effect of filter
                int red = (int) (color.getRed() * 0.393 + color.getGreen() * 0.769 + color.getBlue() * 0.189);
                int green = (int) (color.getRed() * 0.349 + color.getGreen() * 0.686 + color.getBlue() * 0.168);
                int blue = (int) (color.getRed() * 0.272 + color.getGreen() * 0.534 + color.getBlue() * 0.131);

                //new color values after calculation
                red = Math.min(255, red);
                green = Math.min(255, green);
                blue = Math.min(255, blue);

                //applying new color to the bufferImage
                Color sepiaColor = new Color(red, green, blue);
                sepiaImage.setRGB(x, y, sepiaColor.getRGB());
            }
        }
        return sepiaImage;
    }

    //filter for blurBordereffect
    private static BufferedImage applyBlurBorderFilter(BufferedImage image) 
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int borderWidth = width/6;

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Copy the original image to the result image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                resultImage.setRGB(x, y, image.getRGB(x, y));
            }
        }

        // Apply a blur effect to the border
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x < borderWidth || x >= width - borderWidth || y < borderWidth || y >= height - borderWidth) {
                    // Apply the blur effect to the border pixels
                    int red = 0, green = 0, blue = 0;
                    int count = 0;

                    for (int i = -borderWidth; i <= borderWidth; i++) {
                        for (int j = -borderWidth; j <= borderWidth; j++) {
                            int newX = x + i;
                            int newY = y + j;

                            if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                                Color color = new Color(image.getRGB(newX, newY));
                                red += color.getRed();
                                green += color.getGreen();
                                blue += color.getBlue();
                                count++;
                            }
                        }
                    }

                    red /= count;
                    green /= count;
                    blue /= count;

                    Color blurredColor = new Color(red, green, blue);
                    resultImage.setRGB(x, y, blurredColor.getRGB());
                }
            }
        }

        return resultImage;
    }

    //method to save processed images into a folder outputimages
    private static void save(BufferedImage image, String imagePath) 
    {
        try 
        {
            //defining path
            File outputImageFile = new File("outputimages/"+imagePath);
            ImageIO.write(image, "png", outputImageFile);
            System.out.println("Image saved.");
        } 
        catch (IOException e) 
        {
            System.err.println("Error saving the image: " + e.getMessage());
        }
    }

    //method to stop the execution and exit the program
    private static void exit()
    {
        System.exit(0);
    }
}
