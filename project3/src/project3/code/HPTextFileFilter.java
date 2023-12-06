package project3.code;

import java.io.File;
import java.io.FileFilter;

class HPTextFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        return file.isFile() && file.getName().toLowerCase().endsWith(".txt");
    }
}