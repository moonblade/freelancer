package com.dng.gruupy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class pdf_listing extends ListActivity{
	  private File currentDir;
	    private FileArrayAdapter adapter;
	    Global global;
	    Dialog dlog;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        currentDir = new File("/sdcard/");
	        global=(Global)getApplicationContext();
	        fill(currentDir);
	    }
	    private void fill(File f)
	    {
	    	File[]dirs = f.listFiles();
			 this.setTitle("Current Dir: "+f.getName());
			 List<Option>dir = new ArrayList<Option>();
			 List<Option>fls = new ArrayList<Option>();
			 try{
				 for(File ff: dirs)
				 {
					if(ff.isDirectory())
						dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
					else
						if(ff.getName().contains(".pdf"))
					{
						fls.add(new Option(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
					}
				 }
			 }catch(Exception e)
			 {
				 
			 }
			 Collections.sort(dir);
			 Collections.sort(fls);
			 dir.addAll(fls);
			 if(!f.getName().equalsIgnoreCase("sdcard"))
				 dir.add(0,new Option("..","Parent Directory",f.getParent()));
			 adapter = new FileArrayAdapter(pdf_listing.this,R.layout.file_view,dir);
			 this.setListAdapter(adapter);
	    }
	    @Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			Option o = adapter.getItem(position);
			if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
					currentDir = new File(o.getPath());
					fill(currentDir);
			}
			else
			{
				onFileClick(o);
			}
		}
	    private void onFileClick(final Option o)
	    {
	    	Toast.makeText(this, "File Clicked: "+o.getPath(), Toast.LENGTH_SHORT).show();
	    	global.setPdf_path(o.getPath());
	    	dlog=new Dialog(pdf_listing.this);
	    	dlog.setTitle("Choose action:");
	    	dlog.setContentView(R.layout.school_dialog);
	    	Button b1=(Button)dlog.findViewById(R.id.teacher);
	    	b1.setText("View pdf");
	    	Button b2=(Button)dlog.findViewById(R.id.student);
	    	b2.setText("Upload");
	    	b1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openPdfIntent(o.getPath());
				}
			});
	    	b2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dlog.dismiss();
//			    	startActivity(new Intent(pdf_listing.this,Upload_Content.class));
				}
			});
	    	dlog.show();
//	    	startActivity(new Intent(pdf_listing.this,Upload_Video.class));
	    }
	    
	    @Override
	    protected void onResume() {
	    	// TODO Auto-generated method stub
	    	super.onResume();
	    }
		// ------------Open a pdf file to view----------------------//
		private void openPdfIntent(String path) {
			try {
				final Intent intent = new Intent(this,
						Pdf_view.class);
				intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
