package com.repp.todo.crutches;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.repp.todo.R;

/**
 * Диалог открытия файла. Позволяет задавать стартовую директорию,
 * фильтр расширений и лисенер для получения возвращаемого файла.
 * 
 * @author spy
 *
 */
public class OpenFileDialog extends Dialog implements OnClickListener
{
	/**
	 * Интерфейс лисенера для получения файла.
	 */
	public interface OnFileSelectedListener
	{
		public void onFileSelected(File f);
	}
	
	private static final String FILE_KEY = "filename";
	private static final String IMAGE_KEY = "fileimage";
	
	private File currentDir = new File("/");
	private ListView view = null;
	private FilenameFilter filter = null;
	private OnFileSelectedListener onFileSelectedListener = null;
	
	/**
	 * Конструктор.
	 * @param context контекст, в котором запускается диалог.
	 * @param dir стартовая директория.
	 * @param fileExt фильтр расширений, которые должны быть отображены. Если пуст - отображается все.
	 * @param listener лисенер для возвращаемого файла.
	 */
	public OpenFileDialog(Context context, String dir, String[] fileExt, OnFileSelectedListener listener)
	{
		super(context);
		init(dir, fileExt, listener);
	}
	
	public void onClick(View v)
	{
		browseUp();
	}
	
	/** Инициализирует компоненты диалога. */
	private void init(String dir, String[] fileExt, OnFileSelectedListener listener)
	{
		onFileSelectedListener = listener;
		
		if (dir != null && new File(dir).exists())
			currentDir = new File(dir);
		
		prepareFileFilter(fileExt);
		
		setContentView(R.layout.ofd_layout);
		setTitle(R.string.ofd_title);
		
		view = (ListView)findViewById(R.id.ofd_list);
		browseTo(currentDir);
		
		view.setOnItemClickListener(
        		new OnItemClickListener()
        		{
					@SuppressWarnings("unchecked")
					public void onItemClick(AdapterView<?> a, View v, int position, long id)
					{
						Map<String, String> listitem = (Map<String, String>)a.getItemAtPosition(position);
						String text = listitem.get(FILE_KEY);
						File file = new File(currentDir.getAbsolutePath() + File.separator + text);
						if (!browseTo(file) && onFileSelectedListener != null)
						{
							onFileSelectedListener.onFileSelected(file);
							dismiss();
						}
					}
        		});
		
		Button upButton = (Button)findViewById(R.id.ofd_go_up);
		upButton.setOnClickListener(this);
	}
	
	/** Настраивает фильтр расширений отображаемых файлов. */
	private void prepareFileFilter(final String[] ext)
	{
		if (ext == null)
			return;
		
		filter = 
			new FilenameFilter()
			{
				public boolean accept(File dir, String filename)
				{
					if (new File(dir + File.separator + filename).isDirectory())
						return true;
					for (String e : ext)
					{
						if (filename.endsWith(e))
							return true;
					}
					return false;
				}
			};
	}
	
	/**
	 * Переход к указанной директории.
	 * @param dir директория для отображения.
	 * @return true - если перешли к директории, false - если попали на файл.
	 */
	private boolean browseTo(File dir)
	{
		if (!dir.isDirectory())
		{
			return false;
		}
		else
		{
			fillListView(dir);
			currentDir = dir;
			TextView pathView = (TextView)findViewById(R.id.ofd_current_path);
			pathView.setText(currentDir.getAbsolutePath());
			return true;
		}
	}
	
	/** Возврат в директорию на уровень выше. */
	private void browseUp()
	{
		if (currentDir.getParentFile() != null)
			browseTo(currentDir.getParentFile());
	}
	
	/** Заполняет список отображения элементами (файлами и директориями) из переданной директории. */
	private void fillListView(File dir)
	{
		List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
		String[] files = filter != null ? dir.list(filter) : dir.list();
        if (files == null) {
            files = new String[0];
        }
		for (String file : files)
		{
			Map<String, Object> item = new HashMap<String, Object>();
	        item.put(FILE_KEY, file);
	        int imageid = new File(dir.getAbsolutePath() + File.separator + file).isDirectory()
	        	? R.drawable.ic_osdialogs_dir
	        	: R.drawable.ic_osdialogs_file;
	        item.put(IMAGE_KEY, imageid);
	        list.add(item);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(getContext(),
				list,
				R.layout.ofd_list_item,
				new String[] { FILE_KEY, IMAGE_KEY },
				new int[] { R.id.ofd_item_text, R.id.ofd_item_image });
		view.setAdapter(adapter);
	}
	
}
