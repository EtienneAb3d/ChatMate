package com.cubAIx.ChatMate;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class ChateMateGui {
	static final public String _VERSION = "0.1.0";
	
	public Display display = null;
	public Shell shell = null;
	
	public Color mainBckC = null;
	public Color whiteC = null;
	
	Config currentConfig = null;
	
	Text configT = null;
	Text suffixT = null;
	Text modelT = null;
	Text keyT = null;

	Text systemT = null;
	Text userT = null;
	Text assistantT = null;
	
	Text filesT = null;
	Text processedT = null;
	
	public ChateMateGui() {
		currentConfig = new Config();
		currentConfig.load();
	}
	
	void start() {
		try {
			// Create interface
			createInterface();
			try {
				while (!shell.isDisposed()) {
					try {
						if (!display.readAndDispatch()) {
							display.sleep();
						}
					} catch (Throwable t) {
						// Not handled ?
						System.err.println("Not handled error .. ");
						t.printStackTrace(System.err);
					}
					// System.out.println("Main SWT event loop");
				}
			} catch (Throwable t) {
				t.printStackTrace(System.err);
			}
			display.dispose();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
		System.exit(0);
	}
	
	void createInterface() {
		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.open();
		shell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				paint();
			}
		});
		
		mainBckC = new Color(display, 0, 0, 100);
		whiteC = display.getSystemColor(SWT.COLOR_WHITE);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		shell.setLayout(gridLayout);
		shell.setText("ChatMate "+_VERSION);

		createConfigKeyForm();
		createSystemForm();
		createTestForm();
		createFileForm();
		
		shell.pack();
		Rectangle aR = display.getBounds();
		if(aR.width > 1920) {
			aR.width = 1920;
		}
		if(aR.height > 1080) {
			aR.height = 1080;
		}
		aR.width = (aR.width*2)/3;
		aR.height =(aR.height*2)/3;
		shell.setBounds(aR);
	}
	
	void createConfigKeyForm() {
		Composite aC = new Composite(shell, SWT.NULL);
		aC.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout aGL = new GridLayout();
		aGL.numColumns = 8;
		aGL.marginWidth = 0;
		aGL.marginHeight = 0;
		aGL.horizontalSpacing = 5;
		aGL.verticalSpacing = 0;
		aC.setLayout(aGL);
		
		GridData aGD;
		Label aL;

		aL = new Label(aC, SWT.NONE);
		aL.setText("Config");
		aGD = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		aGD.widthHint = 100;
		aL.setLayoutData(aGD);
		
		configT = new Text(aC, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		aGD = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		aGD.widthHint = 100;
		configT.setLayoutData(aGD);
		configT.setText(currentConfig.name);
		configT.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String aName = configT.getText();
				if(!aName.equals(currentConfig.name)) {
					currentConfig.name = aName;
					if(currentConfig.load()) {
						setConfig();
					}
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		
		aL = new Label(aC, SWT.NONE);
		aL.setText("Suffix");
		aGD = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		aGD.widthHint = 100;
		aL.setLayoutData(aGD);
		
		suffixT = new Text(aC, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		aGD = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		aGD.widthHint = 100;
		suffixT.setLayoutData(aGD);
		suffixT.setText(currentConfig.suffix);
		suffixT.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String aSuffix = suffixT.getText();
				if(!aSuffix.equals(currentConfig.model)) {
					currentConfig.suffix = aSuffix;
					currentConfig.save();
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		
		aL = new Label(aC, SWT.NONE);
		aL.setText("Model");
		aGD = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		aGD.widthHint = 100;
		aL.setLayoutData(aGD);
		
		modelT = new Text(aC, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		aGD = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		aGD.widthHint = 100;
		modelT.setLayoutData(aGD);
		modelT.setText(currentConfig.model);
		modelT.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String aModel = modelT.getText();
				if(!aModel.equals(currentConfig.model)) {
					currentConfig.model = aModel;
					currentConfig.save();
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		
		aL = new Label(aC, SWT.NONE);
		aL.setText("Key");
		aGD = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		aGD.widthHint = 100;
		aL.setLayoutData(aGD);
		
		keyT = new Text(aC, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		keyT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		keyT.setText(currentConfig.key.replaceAll(".", "*"));
		keyT.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String aKey = keyT.getText();
				if(!aKey.equals(currentConfig.key)) {
					currentConfig.key = aKey;
					currentConfig.save();
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		keyT.addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseHover(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExit(MouseEvent arg0) {
				keyT.setText(currentConfig.key.replaceAll(".", "*"));
			}
			
			@Override
			public void mouseEnter(MouseEvent arg0) {
				keyT.setText(currentConfig.key);
			}
		});
	}
	
	void createSystemForm() {
		Composite aC = new Composite(shell, SWT.NULL);
		aC.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		aC.setBackground(mainBckC);

		GridLayout aGL = new GridLayout();
		aGL.numColumns = 1;
		aGL.marginWidth = 0;
		aGL.marginHeight = 0;
		aGL.horizontalSpacing = 5;
		aGL.verticalSpacing = 0;
		aC.setLayout(aGL);
		
		GridData aGD;
		Label aL;

		aL = new Label(aC, SWT.NONE);
		aL.setText("System (behave as...)");
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aL.setLayoutData(aGD);
		aL.setBackground(mainBckC);
		aL.setForeground(whiteC);
		
		systemT = new Text(aC, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL);
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aGD.heightHint = 100;
		systemT.setLayoutData(aGD);
		systemT.setText(currentConfig.system);
		systemT.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String aSystem = systemT.getText();
				if(!aSystem.equals(currentConfig.system)) {
					currentConfig.system = aSystem;
					currentConfig.save();
				}
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
	}

	void createTestForm() {
		Composite aC = new Composite(shell, SWT.NULL);
		aC.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		aC.setBackground(mainBckC);

		GridLayout aGL = new GridLayout();
		aGL.numColumns = 2;
		aGL.marginWidth = 0;
		aGL.marginHeight = 0;
		aGL.horizontalSpacing = 5;
		aGL.verticalSpacing = 0;
		aGL.makeColumnsEqualWidth = true;
		aC.setLayout(aGL);
		
		GridData aGD;
		Label aL;

		aL = new Label(aC, SWT.NONE);
		aL.setText("User (data to be processed)");
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aL.setLayoutData(aGD);
		aL.setBackground(mainBckC);
		aL.setForeground(whiteC);

		aL = new Label(aC, SWT.NONE);
		aL.setText("Assistant (ChatGPT result)");
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aL.setLayoutData(aGD);
		aL.setBackground(mainBckC);
		aL.setForeground(whiteC);

		userT = new Text(aC, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL);
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aGD.heightHint = 200;
		userT.setLayoutData(aGD);

		assistantT = new Text(aC, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL);
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aGD.heightHint = 200;
		assistantT.setLayoutData(aGD);
		
		Button button = new Button(aC, SWT.PUSH);
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aGD.horizontalSpan = 2;
		button.setLayoutData(aGD);
		button.setText("Test");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String aUserContent = userT.getText();
				if(aUserContent.trim().isEmpty()) {
					//??
					return;
				}
				shell.setCursor(new Cursor(display, SWT.CURSOR_WAIT));
				final Task aT = new Task(currentConfig,aUserContent);
				Thread aTh = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							aT.process();
						} catch (Exception e2) {
							aT.assistantContent = e2.getMessage();
						}
						display.asyncExec(new Thread(new Runnable() {
							@Override
							public void run() {
								assistantT.setText(aT.assistantContent);
								shell.setCursor(null);
							}
						}));
					}
				});
				aTh.start();
			}
		});
		
	}

	void createFileForm() {
		Composite aC = new Composite(shell, SWT.NULL);
		aC.setLayoutData(new GridData(GridData.FILL_BOTH));
		aC.setBackground(mainBckC);

		GridLayout aGL = new GridLayout();
		aGL.numColumns = 2;
		aGL.marginWidth = 0;
		aGL.marginHeight = 0;
		aGL.horizontalSpacing = 5;
		aGL.verticalSpacing = 0;
		aGL.makeColumnsEqualWidth = true;
		aC.setLayout(aGL);
		
		GridData aGD;
		Label aL;

		aL = new Label(aC, SWT.NONE);
		aL.setText("Files to be processed (drag and drop)");
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aL.setLayoutData(aGD);
		aL.setBackground(mainBckC);
		aL.setForeground(whiteC);

		aL = new Label(aC, SWT.NONE);
		aL.setText("Processed files (ChatGPT result)");
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aL.setLayoutData(aGD);
		aL.setBackground(mainBckC);
		aL.setForeground(whiteC);

		filesT = new Text(aC, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL);
		aGD = new GridData(GridData.FILL_BOTH);
		aGD.heightHint = 200;
		filesT.setLayoutData(aGD);
		
		DropTarget dt = new DropTarget(filesT, DND.DROP_DEFAULT | DND.DROP_MOVE );
		dt.setTransfer(new Transfer[] { FileTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter() {
			public void drop(DropTargetEvent event) {
				StringBuffer aSB = new StringBuffer();
				aSB.append(filesT.getText());
				String fileList[] = null;
				FileTransfer ft = FileTransfer.getInstance();
				if (ft.isSupportedType(event.currentDataType)) {
					fileList = (String[])event.data;
					for(String aF : fileList){
						System.out.println("DROPFILE:"+aF);
						aSB.append(aF).append("\n");
					}
				}
				filesT.setText(aSB.toString());
			}
		});


		processedT = new Text(aC, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL);
		aGD = new GridData(GridData.FILL_BOTH);
		aGD.heightHint = 200;
		processedT.setLayoutData(aGD);
		
		Button button = new Button(aC, SWT.PUSH);
		aGD = new GridData(GridData.FILL_HORIZONTAL);
		aGD.horizontalSpan = 2;
		button.setLayoutData(aGD);
		button.setText("Process all files");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String aUserContent = filesT.getText();
				if(aUserContent.trim().isEmpty()) {
					//??
					return;
				}
				shell.setCursor(new Cursor(display, SWT.CURSOR_WAIT));
				processedT.setText("");
				final String aFileList = filesT.getText();
				final StringBuffer aProcessedSB = new StringBuffer(); 
				Thread aTh = new Thread(new Runnable() {
					@Override
					public void run() {
						for(String aPath : aFileList.split("\n")) {
							File aFile = new File(aPath);
							if(!aFile.exists()) {
								aProcessedSB.append("File not found\n");
							}
							else {
								Task aT = new Task(currentConfig,aFile);
								try {
									aT.process();
									aT.saveResult();
									aProcessedSB.append(aT.savePath).append("\n");
								} catch (Exception e2) {
									aProcessedSB.append("ERROR:").append(e2.getMessage()).append("\n");
								}
							}
							display.asyncExec(new Thread(new Runnable() {
								@Override
								public void run() {
									processedT.setText(aProcessedSB.toString());
								}
							}));
						}
						display.asyncExec(new Thread(new Runnable() {
							@Override
							public void run() {
								shell.setCursor(null);
							}
						}));
					}
				});
				aTh.start();
			}
		});
		
	}

	void setConfig() {//Assume it's the SWT thread!
		configT.setText(currentConfig.name);
		suffixT.setText(currentConfig.suffix);
		modelT.setText(currentConfig.model);
		keyT.setText(currentConfig.key.replaceAll(".", "*"));
		
		systemT.setText(currentConfig.system);
	}
	
	void paint() {
		Rectangle aRect = shell.getBounds();
		GC aMainGC = new GC(shell);
		aMainGC.setClipping(0, 0, aRect.width, aRect.height);
		aMainGC.setBackground(mainBckC);
		aMainGC.fillRectangle(0, 0, aRect.width, aRect.height);
		aMainGC.dispose();
	}

	public static void main(String[] args) {
		System.out.println("Java version : " + System.getProperty("java.version"));
		System.out.println("JRE location : " + System.getProperty("java.home"));
		System.out.println("SWT version : " + SWT.getVersion());
		new ChateMateGui().start();
	}
}
