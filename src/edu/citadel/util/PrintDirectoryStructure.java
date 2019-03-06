package edu.citadel.util;


import java.io.*;


/**
 * Utility class that prints the directory structure to standard output
 * showing the composition of nested files and subdirectories.
 */
public class PrintDirectoryStructure
{
    // Created one string builder to collect all directory/file information
    private static StringBuilder stringBuilder = new StringBuilder();

    /**
     * Prints the structure for the file whose path name is given in arg[0].
     */
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            printUsage();
            System.exit(-1);
        }

        String pathName = args[0];
        File file = new File(pathName);

        if (file.exists())
            printTree(file);
        else
            System.out.println("*** File " + pathName + " does not exist. ***");
    }

    /*
     * Wrapper method that prints the path structure at once using the string builder.
     */
    public static void printTree(File file)
    {
        if (file.isDirectory()) {
            addDirectory(file, 0);
            System.out.println(stringBuilder.toString());
        } else {
            System.out.println("Contents is a file, so nothing to print.");
        }
    }

    // FIXME Needs to print the root file too
    /*
     * Recursive function that adds the directory and formatting to the string builder
     */
    private static void addDirectory(File dir, int nestingLevel)
    {
        // Hidden mac files were being printed so I added a hidden file filter
        // Only accept the file, if the file is not hidden
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return !file.isHidden();
            }
        });

        // For every component, print either the composite or the leaf.
        // If composite, do it again
        for(File file : files) {
            if (file.isDirectory()) {
                stringFormattingHelper(nestingLevel, "+", file.getName());
                addDirectory(file, nestingLevel + 1);
            } else {
                addFile(file, nestingLevel);
            }
        }
    }


    /*
     * Function that adds the file with its formatting to the string builder
     */
    private static void addFile(File file, int nestingLevel)
    {
        stringFormattingHelper(nestingLevel, "-", file.getName());
    }


    /*
     * Function that adds the indention string to the string builder
     */
    private static void addIndentString(int nestingLevel)
    {
        for (int i = 0; i < nestingLevel; i++)
            stringBuilder.append("   ");
    }

    /*
     * Function that appends all information needed for the file/directory to the string builder
     */
    private static void stringFormattingHelper(int nestingLevel, String marker, String fileName) {

        addIndentString(nestingLevel);
        stringBuilder.append(marker).append(" ");
        stringBuilder.append(fileName);
        stringBuilder.append("\n");
    }


    private static void printUsage()
    {
        System.out.println("Usage: java edu.citadel.util.PrintDirectoryStructure(<path>)");
        System.out.println("       where <path> is the path of a file or directory");
        System.out.println();
    }
}