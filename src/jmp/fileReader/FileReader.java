package jmp.fileReader;

import jmp.readenFile.ReadenFile;

public abstract class FileReader 
{
	protected String filePath;
	
	public FileReader(String path) 
	{
		filePath = path;
	}
	
	public abstract ReadenFile read(int x, int y);
	
	public abstract ReadenFile read(String x, String y);
	
}
