package org.medcare.igtl.tests.gui;


//import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
//import javax.swing.JSeparator;

//import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JTabbedPane;
import javax.swing.JButton;

//import javax.swing.JScrollPane;

//import javax.swing.JSeparator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import com.neuronrobotics.nrconsole.plugin.PID.PIDControlGui;
import com.neuronrobotics.sdk.genericdevice.GenericPIDDevice;
//import com.neuronrobotics.sdk.pid.IPIDControl;
import com.neuronrobotics.sdk.pid.IPIDEventListener;
import com.neuronrobotics.sdk.pid.PIDEvent;
import com.neuronrobotics.sdk.pid.PIDLimitEvent;
import com.neuronrobotics.sdk.ui.ConnectionDialog;
//import java.awt.Cursor;

import net.miginfocom.swing.MigLayout;

public class KinematicsGUI implements IPIDEventListener{
	/**
	 * This method initializes textFieldXAxisJointPosition
	 * 	
	 * @return javax.swing.JTextField	
	 */

	public static void main(String[] args) {
		//@SuppressWarnings("unused")
		new KinematicsGUI();
		//application.initialize();
	}

	private static final long serialVersionUID = 1L;
	private JTabbedPane jTabbedPane = null;
	private SlicerPanel slicerPanel = null;
	private JPanel motionPanel = null;

	//Custom 
	private JFrame frame;
	private JButton connect = new JButton("Connect");
	private boolean connected = false;
	private GenericPIDDevice device = new GenericPIDDevice();
	private PIDControlGui pidControl;
	private int [] values = null;
	/**
	 * This is the default constructor
	 */
	public KinematicsGUI() {
		super();
		initialize();
	}
	public KinematicsGUI getKinematicsGUI(){
		return this;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		JPanel content = new JPanel(new MigLayout());
		
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!connected){
					if (!ConnectionDialog.getBowlerDevice(getDevice())) {
						throw new RuntimeException("Failed to connect");
					}
					setValues(device.GetAllPIDPosition());
					device.addPIDEventListener(getKinematicsGUI());
					pidControl = new PIDControlGui(getDevice());
					pidControl.setSize(new Dimension(1024, 768));
					motionPanel.add(pidControl);
				}else{
					getDevice().disconnect();
				}
				connected=getDevice().isAvailable();
				if(connected){
					connect.setText("Disconnect");
				}else{
					connect.setText("Connect");
				}
				getJTabbedPane().setVisible(connected);
				frame.setVisible(true);
			}
		});
		
		content.add(connect,"wrap");
		content.add(getJTabbedPane(),"wrap");
		getJTabbedPane().setVisible(false);
		
		
		frame = new JFrame();
		frame.setContentPane(content);
		frame.setSize(new Dimension(1024, 768));
		frame.setPreferredSize(new Dimension(1024, 768));
		frame.setMaximumSize(new Dimension(2147, 2147));
		//
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//	frame.setSize(new Dimension( 300,  300));
		Dimension windowSize = frame.getSize();

		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		frame.setLocation(windowX, windowY);  // Don't use "f." inside constructor.
		//
		
		frame.setTitle("MRI Needle Robot Controller");
		// Kevin close when windows is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setName("MRI Needle Robot Controller Tabs");
			jTabbedPane.setSize(new Dimension(1024, 768));
			jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			//jTabbedPane
		//	ImageIcon icon = createImageIcon("middle.gif");
			jTabbedPane.addTab("Slicer Panel", new ImageIcon(KinematicsGUI.class.getResource("middle.gif")), getSlicerPanel(), null);
			jTabbedPane.addTab("Motion Control Panel", new ImageIcon(KinematicsGUI.class.getResource("middle.gif")), getMotionPanel(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes slicerPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSlicerPanel() {
		if (slicerPanel == null) {
			slicerPanel = new SlicerPanel(this);
		}
		return slicerPanel;
	}

	/**
	 * This method initializes motionPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMotionPanel() {
		if (motionPanel == null) {
			motionPanel = new JPanel();
		}
		return motionPanel;
	}

	public GenericPIDDevice getDevice() {
		return device;
	}

	@Override
	public void onPIDEvent(PIDEvent e) {
		System.out.println("Got a PID event in Kinematics GUI #"+e.getGroup()+" value: "+e.getValue()+" timestamp: "+e.getTimeStamp());
		getValues()[e.getGroup()]=e.getValue();
	}

	@Override
	public void onPIDLimitEvent(PIDLimitEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPIDReset(int group, int currentValue) {
		// TODO Auto-generated method stub
		
	}
	public void setValues(int [] values) {
		this.values = values;
	}
	public int [] getValues() {
		return values;
	}
} 
