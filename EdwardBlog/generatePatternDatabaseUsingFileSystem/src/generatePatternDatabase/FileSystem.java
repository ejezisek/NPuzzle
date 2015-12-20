package generatePatternDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class FileSystem implements StorageSystem {

	
	private String defaultFileLocation;
	
	public FileSystem(String defaultFileLocation) {
		super();
		this.defaultFileLocation = defaultFileLocation;
	}

	@Override
	public void addMultiple(String[] location, String []value, int[] numToDestination) {
		String currentLocation=null;
		RandomAccessFile f = null;
		List<String> currentFile=null;
		ListIterator<String> it=null;
		for(int i=0; i<location.length; i++)
		{

			if(currentLocation==null || !location[i].equals(currentLocation))
			{
				//Write the currentFile to disk
				if(currentLocation!=null)
				{			
					FileWriter fw=null;
					try {
						fw=new FileWriter(defaultFileLocation+currentLocation);
						for(String val:currentFile)
						{
							fw.write(val+"\n");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally
					{
						try {
							fw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
				currentFile=new LinkedList<String>();
				currentLocation=location[i];
				if(f!=null)
					try {
						f.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				File file=new File(defaultFileLocation+currentLocation);
				if(file.exists())
				{
				BufferedReader br=null;
				try {

						br=new BufferedReader(new FileReader(file));
						for(;;)
						{
							String line=br.readLine();
							if(line==null)
								break;
							currentFile.add(line);
						}
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
				it=currentFile.listIterator();
			}
			String currentValue=value[i];
			for(;;)
			{
				if(it.hasNext())
				{
					String nextVal=it.next().substring(0, 6);
					if(currentValue.compareTo(nextVal)<0)
					{
						it.previous();
						it.add(currentValue+"|"+numToDestination[i]);
						break;
					}
				}
				else
				{
					it.add(currentValue+"|"+numToDestination[i]);
					break;
				}
			}
		}
		//Write the currentFile to disk
		if(currentLocation!=null)
		{			
			FileWriter fw=null;
			try {
				fw=new FileWriter(defaultFileLocation+currentLocation);
				for(String val:currentFile)
				{
					fw.write(val+"\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public boolean[] exists(String []location, String []value) {
		boolean []results=new boolean[location.length];
		String currentVal=new String();
		BufferedReader br=null;
		String currentFileName=new String();
		for(int i=0; i<location.length; i++)
		{
			String wantedValue=value[i];
			String filename=defaultFileLocation+location[i];
			if(currentFileName.equals(filename))
			{
				if(br==null)
				{
					results[i]=false;
					continue;
				}
				if(currentVal.equals(wantedValue))
				{
					results[i]=true;
					continue;
				}
				for(;;)
				{
					try {
						currentVal=br.readLine();
						currentVal=currentVal.substring(0, 6);
						int cmpVal=currentVal.compareTo(wantedValue);
						if(cmpVal==0)
							results[i]=true;
						else if(cmpVal>0)
							results[i]=false;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else
			{
				if(br!=null)
				{
					try {
						br.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				File f=new File(filename);
				if(f.exists())
				{
					//Open the file read until it gets to the correct position.
					try {
							br=new BufferedReader(new FileReader(f));
						for(;;)
						{
							currentVal=br.readLine();
							if(currentVal==null)
							{
								results[i]=false;
								break;
							}
							currentVal=currentVal.substring(0, 6);
							int cmpVal=currentVal.compareTo(wantedValue);
							if(cmpVal==0)
							{
								results[i]=true;
								break;
							}
							
							else if(cmpVal>0)
							{
								results[i]=false;
								break;
							}
						}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally{ 
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else
			{
				results[i]=false;
				br=null;
			}
		}
	}
		return results;

	}
}
