package org.medcare.igtl.tests.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;

import com.neuronrobotics.sdk.genericdevice.GenericPIDDevice;

public class SlicerPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3485775714533234856L;
	private JLabel jLabel = null;
	private JSlider jSlider = null;
	//jButton
	
	private JButton jButtonCalculcateInvKinematics = null;
	private JTable tableZFrameRegistration;
	private JTextField textFieldXAxisDesiredJointPosition;
	private JTextField textFieldYAxisDesiredJointPosition;
	private JTextField textFieldZAxisDesiredJointPosition;
	private JTextField textFieldZAxisJointPosition;
	private JTextField textFieldInsertionDesiredJointPosition;
	private JTextField textFieldRotationDesiredJointPosition;
	private JTextField textFieldInsertionJointPosition;
	private JTextField textFieldRotationJointPosition;
	private JTextField textFieldRetractionDesiredJointPosition;
	private JTextField textFieldRetractionJointPosition;
	private JSeparator separator;
	private JSeparator separator_1;
	private JLabel lblZFrameRegistrition;
	private JTable tableDesiredNeedleRAS;
	private JLabel lblDesiredNeedleRas;
	private JTable tableActualNeedleRAS;
	private JLabel lblActualNeedleRas;

	private JLabel lblRoll = null;
	private JLabel lblPitch = null;
	private JLabel lblYaw = null;
	private JLabel lblPosX = null;
	private JLabel lblPosY = null;
	private JLabel lblPosZ = null;
	private JLabel lblCartesianError = null;
	private JLabel lblJointError = null;

	private JTextField textFieldXAxisJointPosition = null;
	private JTextField textFieldYAxisJointPosition = null;

	private JTextField textFieldXAxisJointError = null;
	private JTextField textFieldYAxisJointError = null;
	private JTextField textFieldZAxisJointError = null;
	private JTextField textFieldInsertionJointError = null;
	private JTextField textFieldRotationJointError = null;
	private JTextField textFieldRetractionJointError = null;
	private JButton jButtonMoveTo = null;
	private JButton jButtonStop = null;

	private JTextField textFieldXAxisNeedleCartesianError = null;
	private JTextField textFieldYAxisNeedleCartesianError = null;
	private JTextField textFieldZAxisNeedleCartesianError = null;
	private JTextField textFieldRollNeedleCartesianError = null;
	private JTextField textFieldXPitchNeedleCartesianError = null;
	private JTextField textFieldYawNeedleCartesianError = null;
	private JButton jButtonRetractNeedle = null;
	private JButton jButtonRetractStylet = null;
	private JButton jButtonHomePosition = null;
	private JButton jButtonZFrameRegistration = null;

	private KinematicsGUI kinematicsGUI;

	public SlicerPanel(KinematicsGUI kinematicsGUI) {
		setKinematicsGUI(kinematicsGUI);
		lblCartesianError = new JLabel("Stage X Axis");
		lblCartesianError.setBounds(new Rectangle(528, 205, 134, 21));
		lblCartesianError.setForeground(Color.BLUE);
		lblCartesianError.setText("Needle RAS Space Error");
		lblCartesianError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPosZ = new JLabel("Stage X Axis");
		lblPosZ.setBounds(new Rectangle(474, 311, 39, 23));
		lblPosZ.setForeground(Color.BLUE);
		lblPosZ.setText("PosZ");
		lblPosZ.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPosY = new JLabel("Stage X Axis");
		lblPosY.setBounds(new Rectangle(473, 273, 37, 22));
		lblPosY.setForeground(Color.BLUE);
		lblPosY.setText("PosY");
		lblPosY.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPosX = new JLabel("Stage X Axis");
		lblPosX.setBounds(new Rectangle(475, 234, 39, 24));
		lblPosX.setForeground(Color.BLUE);
		lblPosX.setText("PosX");
		lblPosX.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblYaw = new JLabel("Stage X Axis");
		lblYaw.setBounds(new Rectangle(472, 413, 42, 24));
		lblYaw.setForeground(Color.BLUE);
		lblYaw.setText("Yaw");
		lblYaw.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPitch = new JLabel("Stage X Axis");
		lblPitch.setBounds(new Rectangle(471, 382, 45, 21));
		lblPitch.setForeground(Color.BLUE);
		lblPitch.setText("Pitch");
		lblPitch.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRoll = new JLabel("Stage X Axis");
		lblRoll.setBounds(new Rectangle(471, 353, 40, 20));
		lblRoll.setForeground(Color.BLUE);
		lblRoll.setText("Roll");
		lblRoll.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblJointError = new JLabel("Stage X Axis");
		lblJointError.setBounds(new Rectangle(386, 207, 62, 22));
		lblJointError.setForeground(Color.BLUE);
		lblJointError.setText("Joint Error");
		lblJointError.setFont(new Font("Tahoma", Font.BOLD, 11));
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(333, 28, 52, 30));
		jLabel.setText("50");

		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(SystemColor.control);
		setLayout(null);
		setName("");
		add(jLabel, null);
		add(getJSlider(), null);

		// make a separator
		//this.add(new JSeparator(JSeparator.HORIZONTAL),BorderLayout.LINE_START);

		tableZFrameRegistration = new JTable();
		tableZFrameRegistration.setBorder(new CompoundBorder());
		tableZFrameRegistration.setModel(new DefaultTableModel(
				new Object[][] {
						{new Double(1.0), new Double(0.0), new Double(0.0), new Double(0.1)},
						{new Double(0.0), new Double(1.0), new Double(0.0), new Double(0.1)},
						{new Double(0.0), new Double(0.0), new Double(1.0), new Double(0.1)},
						{new Double(0.0), new Double(0.0), new Double(0.0), new Double(1.0)},
				},
				new String[] {
						"t", "s", "n", "Position"
				}
		)
		
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -2115861646443740429L;
			Class[] columnTypes = new Class[] {
					Double.class, Double.class, Double.class, Double.class
			};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableZFrameRegistration.getColumnModel().getColumn(0).setResizable(false);
		tableZFrameRegistration.getColumnModel().getColumn(0).setMaxWidth(100);
		tableZFrameRegistration.getColumnModel().getColumn(1).setResizable(false);
		tableZFrameRegistration.getColumnModel().getColumn(2).setResizable(false);
		tableZFrameRegistration.getColumnModel().getColumn(3).setResizable(false);
		tableZFrameRegistration.getColumnModel().getColumn(3).setMaxWidth(100);
		tableZFrameRegistration.setBounds(10, 94, 200, 62);
		
		this.add(tableZFrameRegistration);

		textFieldXAxisDesiredJointPosition = new JTextField();
		textFieldXAxisDesiredJointPosition.setText("0");
		textFieldXAxisDesiredJointPosition.setEditable(false);
		textFieldXAxisDesiredJointPosition.setBounds(126, 241, 120, 20);
		textFieldXAxisDesiredJointPosition.setColumns(10);

		textFieldYAxisDesiredJointPosition = new JTextField();
		textFieldYAxisDesiredJointPosition.setText("0");
		textFieldYAxisDesiredJointPosition.setEditable(false);
		textFieldYAxisDesiredJointPosition.setBounds(126, 280, 120, 20);
		textFieldYAxisDesiredJointPosition.setColumns(10);

		JLabel lblStageyAxis = new JLabel("Stage Y Axis");
		lblStageyAxis.setForeground(Color.BLUE);
		lblStageyAxis.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStageyAxis.setBounds(10, 277, 80, 25);
		this.add(lblStageyAxis);

		JLabel lblStageXAxis = new JLabel("Stage X Axis");
		lblStageXAxis.setForeground(Color.BLUE);
		lblStageXAxis.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStageXAxis.setBounds(10, 240, 80, 25);
		this.add(lblStageXAxis);

		JLabel lblStagezAxis = new JLabel("Stage Z Axis");
		lblStagezAxis.setForeground(Color.BLUE);
		lblStagezAxis.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStagezAxis.setBounds(10, 313, 80, 30);
		this.add(lblStagezAxis);

		JLabel lblDesiredJointPosition = new JLabel("Encoder Count");
		lblDesiredJointPosition.setForeground(Color.BLUE);
		lblDesiredJointPosition.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDesiredJointPosition.setText("Desired Joint Position");
		lblDesiredJointPosition.setBounds(125, 209, 122, 25);

		JLabel lblJointPosition = new JLabel("EncoderPosition");
		lblJointPosition.setForeground(Color.BLUE);
		lblJointPosition.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblJointPosition.setText("Joint Position");
		lblJointPosition.setBounds(271, 210, 80, 20);

		JLabel lblInsertionStroke = new JLabel("Insertion Axis");
		lblInsertionStroke.setForeground(Color.BLUE);
		lblInsertionStroke.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInsertionStroke.setBounds(10, 354, 82, 20);
		this.add(lblInsertionStroke);

		JLabel lblNeedleRotationAxis = new JLabel("Rotation Axis");
		lblNeedleRotationAxis.setForeground(Color.BLUE);
		lblNeedleRotationAxis.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNeedleRotationAxis.setBounds(10, 385, 80, 20);
		this.add(lblNeedleRotationAxis);

		JLabel lblRetractionAxis = new JLabel("Retraction Axis");
		lblRetractionAxis.setForeground(Color.BLUE);
		lblRetractionAxis.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRetractionAxis.setBounds(10, 416, 100, 20);
		this.add(lblRetractionAxis);

		textFieldZAxisDesiredJointPosition = new JTextField();
		textFieldZAxisDesiredJointPosition.setText("0");
		textFieldZAxisDesiredJointPosition.setEditable(false);
		textFieldZAxisDesiredJointPosition.setBounds(125, 319, 120, 20);
		this.add(lblDesiredJointPosition, lblDesiredJointPosition.getName());
		this.add(textFieldXAxisDesiredJointPosition, textFieldXAxisDesiredJointPosition.getName());
		this.add(textFieldYAxisDesiredJointPosition, textFieldYAxisDesiredJointPosition.getName());
		this.add(textFieldZAxisDesiredJointPosition);
		textFieldZAxisDesiredJointPosition.setColumns(10);



		textFieldZAxisJointPosition = new JTextField();
		textFieldZAxisJointPosition.setText("0");
		textFieldZAxisJointPosition.setEditable(false);
		textFieldZAxisJointPosition.setBounds(270, 315, 89, 20);
		textFieldZAxisJointPosition.setColumns(10);

		textFieldInsertionDesiredJointPosition = new JTextField();
		textFieldInsertionDesiredJointPosition.setText("0");
		textFieldInsertionDesiredJointPosition.setEditable(false);
		textFieldInsertionDesiredJointPosition.setColumns(10);
		textFieldInsertionDesiredJointPosition.setBounds(125, 355, 120, 20);
		this.add(textFieldInsertionDesiredJointPosition);

		textFieldRotationDesiredJointPosition = new JTextField();
		textFieldRotationDesiredJointPosition.setText("0");
		textFieldRotationDesiredJointPosition.setEditable(false);
		textFieldRotationDesiredJointPosition.setColumns(10);
		textFieldRotationDesiredJointPosition.setBounds(126, 385, 124, 20);
		this.add(textFieldRotationDesiredJointPosition);

		textFieldInsertionJointPosition = new JTextField();
		textFieldInsertionJointPosition.setText("0");
		textFieldInsertionJointPosition.setEditable(false);
		textFieldInsertionJointPosition.setColumns(10);
		textFieldInsertionJointPosition.setBounds(270, 353, 88, 20);
		this.add(lblJointPosition, lblJointPosition.getName());
		this.add(getTextFieldXAxisJointPosition(), null);
		this.add(getTextFieldYAxisJointPosition(), null);
		this.add(textFieldZAxisJointPosition, textFieldZAxisJointPosition.getName());
		this.add(textFieldInsertionJointPosition);

		textFieldRotationJointPosition = new JTextField();
		textFieldRotationJointPosition.setText("0");
		textFieldRotationJointPosition.setEditable(false);
		textFieldRotationJointPosition.setColumns(10);
		textFieldRotationJointPosition.setBounds(268, 383, 90, 20);
		this.add(textFieldRotationJointPosition);

		textFieldRetractionDesiredJointPosition = new JTextField();
		textFieldRetractionDesiredJointPosition.setText("0");
		textFieldRetractionDesiredJointPosition.setEditable(false);
		textFieldRetractionDesiredJointPosition.setColumns(10);
		textFieldRetractionDesiredJointPosition.setBounds(127, 416, 120, 20);

		textFieldRetractionJointPosition = new JTextField();
		textFieldRetractionJointPosition.setText("0");
		textFieldRetractionJointPosition.setEditable(false);
		textFieldRetractionJointPosition.setColumns(10);
		textFieldRetractionJointPosition.setBounds(270, 414, 92, 20);
		this.add(textFieldRetractionDesiredJointPosition, textFieldRetractionDesiredJointPosition.getName());
		this.add(textFieldRetractionJointPosition);

		separator = new JSeparator();
		separator.setBackground(Color.WHITE);
		separator.setForeground(Color.BLACK);
		separator.setBounds(21, 62, 624, 9);
		this.add(lblJointError, null);
		this.add(getTextFieldXAxisJointError(), null);
		this.add(getTextFieldYAxisJointError(), null);
		this.add(getTextFieldZAxisJointError(), null);
		this.add(getTextFieldInsertionJointError(), null);
		this.add(getTextFieldRotationJointError(), null);

		separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.WHITE);
		separator_1.setBounds(13, 192, 615, 20);
		this.add(getTextFieldRetractionJointError(), null);

		lblZFrameRegistrition = new JLabel("Z Frame Registration");
		lblZFrameRegistrition.setForeground(Color.BLUE);
		lblZFrameRegistrition.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblZFrameRegistrition.setBounds(20, 67, 157, 25);

		tableDesiredNeedleRAS = new JTable();
		tableDesiredNeedleRAS.setModel(new DefaultTableModel(
				new Object[][] {
						{new Double(1.0), new Double(0.0), new Double(0.0), new Double(0.1)},
						{new Double(0.0), new Double(1.0), new Double(0.0), new Double(0.1)},
						{new Double(0.0), new Double(0.0), new Double(1.0), new Double(0.1)},
						{new Double(0.0), new Double(0.0), new Double(0.0), new Double(1.0)},
				},
				new String[] {
						"s", "r", "t", "Position"
				}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1919192436306365374L;
			Class[] columnTypes = new Class[] {
					Double.class, Double.class, Double.class, Double.class
			};
			
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableDesiredNeedleRAS.getColumnModel().getColumn(0).setResizable(false);
		tableDesiredNeedleRAS.getColumnModel().getColumn(0).setMaxWidth(100);
		tableDesiredNeedleRAS.getColumnModel().getColumn(1).setResizable(false);
		tableDesiredNeedleRAS.getColumnModel().getColumn(1).setMaxWidth(100);
		tableDesiredNeedleRAS.getColumnModel().getColumn(2).setResizable(false);
		tableDesiredNeedleRAS.getColumnModel().getColumn(2).setMaxWidth(100);
		tableDesiredNeedleRAS.getColumnModel().getColumn(3).setResizable(false);
		tableDesiredNeedleRAS.getColumnModel().getColumn(3).setMaxWidth(100);
		tableDesiredNeedleRAS.setBounds(220, 94, 200, 62);


		lblDesiredNeedleRas = new JLabel("Desired Needle RAS");
		lblDesiredNeedleRas.setForeground(Color.BLUE);
		lblDesiredNeedleRas.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDesiredNeedleRas.setBounds(231, 68, 167, 25);
		this.add(lblDesiredNeedleRas, lblDesiredNeedleRas.getName());
		this.add(tableDesiredNeedleRAS, tableDesiredNeedleRAS.getName());
		this.add(lblZFrameRegistrition, lblZFrameRegistrition.getName());

		tableActualNeedleRAS = new JTable();
		tableActualNeedleRAS.setModel(new DefaultTableModel(
				new Object[][] {
						{new Double(1.0), new Double(0.0), new Double(0.0), new Double(0.1)},
						{new Double(0.0), new Double(1.0), new Double(0.0), new Double(0.1)},
						{new Double(0.0), new Double(1.0), new Double(1.0), new Double(0.1)},
						{new Double(0.0), new Double(0.0), new Double(0.0), new Double(1.0)},
				},
				new String[] {
						"s", "t", "r", "Position"
				}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5881532877939470175L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				Double.class, Double.class, Double.class, Double.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableActualNeedleRAS.getColumnModel().getColumn(0).setResizable(false);
		tableActualNeedleRAS.getColumnModel().getColumn(0).setMaxWidth(100);
		tableActualNeedleRAS.getColumnModel().getColumn(1).setResizable(false);
		tableActualNeedleRAS.getColumnModel().getColumn(1).setMaxWidth(100);
		tableActualNeedleRAS.getColumnModel().getColumn(2).setResizable(false);
		tableActualNeedleRAS.getColumnModel().getColumn(2).setMaxWidth(100);
		tableActualNeedleRAS.getColumnModel().getColumn(3).setResizable(false);
		tableActualNeedleRAS.getColumnModel().getColumn(3).setMaxWidth(100);
		tableActualNeedleRAS.setBounds(443, 94, 200, 59);

		lblActualNeedleRas = new JLabel("Actual Needle RAS");
		lblActualNeedleRas.setForeground(Color.BLUE);
		lblActualNeedleRas.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblActualNeedleRas.setBounds(471, 68, 167, 25);
		this.add(lblActualNeedleRas, lblActualNeedleRas.getName());
		this.add(tableActualNeedleRAS, tableActualNeedleRAS.getName());
		this.add(separator_1, separator_1.getName());
		this.add(lblCartesianError, null);
		this.add(lblPosX, null);
		this.add(lblPosY, null);
		this.add(lblPosZ, null);
		this.add(lblRoll, null);
		this.add(lblPitch, null);
		this.add(lblYaw, null);
		this.add(getTextFieldXAxisNeedleCartesianError(), null);
		this.add(getTextFieldYAxisNeedleCartesianError(), null);
		this.add(getTextFieldZAxisNeedleCartesianError(), null);
		this.add(getTextFieldRollNeedleCartesianError(), null);
		this.add(getTextFieldXPitchNeedleCartesianError(), null);
		this.add(getTextFieldYawNeedleCartesianError(), null);
		this.add(getJButtonStop(), null);
		this.add(getJButtonMoveTo(), null);
		this.add(getJButton(), null);
		this.add(getJButtonRetractNeedle(), null);
		this.add(getJButtonRetractStylet(), null);
		this.add(getJButtonHomePosition(), null);
		this.add(separator, separator.getName());
		this.add(getJButtonZFrameRegistration(), null);

	}


	/**
	 * This method initializes jSlider	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider();
			jSlider.setBounds(new Rectangle(406, 23, 247, 32));
			jSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					System.out.println("stateChanged()"); 
					int frequency = jSlider.getValue();
					jLabel.setText(Integer.toString(frequency));

					//					String labelText=jLabel.getText();
					//					int newLabelText=Integer.parseInt(labelText)+5;
					//					jLabel.setText(Integer.toString(newLabelText));
				}
			});
		}
		return jSlider;
	}
	/**
	 * This method initializes jButtonCalculcateInvKinematics	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButtonCalculcateInvKinematics == null) {
			jButtonCalculcateInvKinematics = new JButton();
			jButtonCalculcateInvKinematics.setText("Calculate Robot Joint Position");
			jButtonCalculcateInvKinematics.setBounds(new Rectangle(128, 466, 210, 35));
		}
		return jButtonCalculcateInvKinematics;
	}
	private JTextField getTextFieldXAxisJointPosition() {
		if (textFieldXAxisJointPosition == null) {
			textFieldXAxisJointPosition = new JTextField();
			textFieldXAxisJointPosition.setBounds(new Rectangle(269, 239, 86, 20));
			textFieldXAxisJointPosition.setEditable(false);
			textFieldXAxisJointPosition.setColumns(10);
			textFieldXAxisJointPosition.setText("0");
		}
		return textFieldXAxisJointPosition;
	}
	/**
	 * This method initializes textFieldYAxisJointPosition	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldYAxisJointPosition() {
		if (textFieldYAxisJointPosition == null) {
			textFieldYAxisJointPosition = new JTextField();
			textFieldYAxisJointPosition.setBounds(new Rectangle(270, 277, 91, 20));
			textFieldYAxisJointPosition.setEditable(false);
			textFieldYAxisJointPosition.setColumns(10);
			textFieldYAxisJointPosition.setText("0");
		}
		return textFieldYAxisJointPosition;
	}
	/**
	 * This method initializes textFieldXAxisJointError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldXAxisJointError() {
		if (textFieldXAxisJointError == null) {
			textFieldXAxisJointError = new JTextField();
			textFieldXAxisJointError.setBounds(new Rectangle(384, 238, 70, 20));
			textFieldXAxisJointError.setEditable(false);
			textFieldXAxisJointError.setColumns(10);
			textFieldXAxisJointError.setText("0");
		}
		return textFieldXAxisJointError;
	}
	/**
	 * This method initializes textFieldYAxisJointError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldYAxisJointError() {
		if (textFieldYAxisJointError == null) {
			textFieldYAxisJointError = new JTextField();
			textFieldYAxisJointError.setBounds(new Rectangle(382, 277, 70, 20));
			textFieldYAxisJointError.setEditable(false);
			textFieldYAxisJointError.setColumns(10);
			textFieldYAxisJointError.setText("0");
		}
		return textFieldYAxisJointError;
	}
	/**
	 * This method initializes textFieldZAxisJointError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldZAxisJointError() {
		if (textFieldZAxisJointError == null) {
			textFieldZAxisJointError = new JTextField();
			textFieldZAxisJointError.setBounds(new Rectangle(381, 315, 70, 20));
			textFieldZAxisJointError.setEditable(false);
			textFieldZAxisJointError.setColumns(10);
			textFieldZAxisJointError.setText("0");
		}
		return textFieldZAxisJointError;
	}
	/**
	 * This method initializes textFieldInsertionJointError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldInsertionJointError() {
		if (textFieldInsertionJointError == null) {
			textFieldInsertionJointError = new JTextField();
			textFieldInsertionJointError.setBounds(new Rectangle(383, 353, 70, 20));
			textFieldInsertionJointError.setEditable(false);
			textFieldInsertionJointError.setColumns(10);
			textFieldInsertionJointError.setText("0");
		}
		return textFieldInsertionJointError;
	}
	/**
	 * This method initializes textFieldRotationJointError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldRotationJointError() {
		if (textFieldRotationJointError == null) {
			textFieldRotationJointError = new JTextField();
			textFieldRotationJointError.setBounds(new Rectangle(380, 383, 70, 20));
			textFieldRotationJointError.setEditable(false);
			textFieldRotationJointError.setColumns(10);
			textFieldRotationJointError.setText("0");
		}
		return textFieldRotationJointError;
	}
	/**
	 * This method initializes textFieldRetractionJointError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldRetractionJointError() {
		if (textFieldRetractionJointError == null) {
			textFieldRetractionJointError = new JTextField();
			textFieldRetractionJointError.setBounds(new Rectangle(380, 414, 70, 20));
			textFieldRetractionJointError.setEditable(false);
			textFieldRetractionJointError.setColumns(10);
			textFieldRetractionJointError.setText("0");
		}
		return textFieldRetractionJointError;
	}
	/**
	 * This method initializes jButtonMoveTo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonMoveTo() {
		if (jButtonMoveTo == null) {
			jButtonMoveTo = new JButton();
			jButtonMoveTo.setBounds(new Rectangle(128, 510, 210, 35));
			jButtonMoveTo.setText("Insertion Needle");
			jButtonMoveTo.setActionCommand("");
		}
		return jButtonMoveTo;
	}
	/**
	 * This method initializes jButtonStop	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonStop() {
		if (jButtonStop == null) {
			jButtonStop = new JButton();
			jButtonStop.setBounds(new Rectangle(520, 464, 120, 35));
			jButtonStop.setForeground(Color.red);
			jButtonStop.setText("STOP");
			jButtonStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO add the stop event code
					System.out.println("Stopping all motors!");
					getDevice().SetAllPIDSetPoint(getKinematicsGUI().getValues(), 0);
				}
			});
		}
		return jButtonStop;
	}
	/**
	 * This method initializes textFieldXAxisNeedleCartesianError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldXAxisNeedleCartesianError() {
		if (textFieldXAxisNeedleCartesianError == null) {
			textFieldXAxisNeedleCartesianError = new JTextField();
			textFieldXAxisNeedleCartesianError.setBounds(new Rectangle(525, 234, 139, 20));
			textFieldXAxisNeedleCartesianError.setEditable(false);
			textFieldXAxisNeedleCartesianError.setColumns(10);
			textFieldXAxisNeedleCartesianError.setText("0");
		}
		return textFieldXAxisNeedleCartesianError;
	}
	/**
	 * This method initializes textFieldYAxisNeedleCartesianError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldYAxisNeedleCartesianError() {
		if (textFieldYAxisNeedleCartesianError == null) {
			textFieldYAxisNeedleCartesianError = new JTextField();
			textFieldYAxisNeedleCartesianError.setBounds(new Rectangle(525, 270, 140, 20));
			textFieldYAxisNeedleCartesianError.setEditable(false);
			textFieldYAxisNeedleCartesianError.setColumns(10);
			textFieldYAxisNeedleCartesianError.setText("0");
		}
		return textFieldYAxisNeedleCartesianError;
	}
	/**
	 * This method initializes textFieldZAxisNeedleCartesianError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldZAxisNeedleCartesianError() {
		if (textFieldZAxisNeedleCartesianError == null) {
			textFieldZAxisNeedleCartesianError = new JTextField();
			textFieldZAxisNeedleCartesianError.setBounds(new Rectangle(526, 310, 140, 19));
			textFieldZAxisNeedleCartesianError.setEditable(false);
			textFieldZAxisNeedleCartesianError.setColumns(10);
			textFieldZAxisNeedleCartesianError.setText("0");
		}
		return textFieldZAxisNeedleCartesianError;
	}
	/**
	 * This method initializes textFieldRollNeedleCartesianError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldRollNeedleCartesianError() {
		if (textFieldRollNeedleCartesianError == null) {
			textFieldRollNeedleCartesianError = new JTextField();
			textFieldRollNeedleCartesianError.setBounds(new Rectangle(526, 352, 138, 20));
			textFieldRollNeedleCartesianError.setEditable(false);
			textFieldRollNeedleCartesianError.setColumns(10);
			textFieldRollNeedleCartesianError.setText("0");
		}
		return textFieldRollNeedleCartesianError;
	}
	/**
	 * This method initializes textFieldXPitchNeedleCartesianError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldXPitchNeedleCartesianError() {
		if (textFieldXPitchNeedleCartesianError == null) {
			textFieldXPitchNeedleCartesianError = new JTextField();
			textFieldXPitchNeedleCartesianError.setBounds(new Rectangle(527, 385, 140, 17));
			textFieldXPitchNeedleCartesianError.setEditable(false);
			textFieldXPitchNeedleCartesianError.setColumns(10);
			textFieldXPitchNeedleCartesianError.setText("0");
		}
		return textFieldXPitchNeedleCartesianError;
	}
	/**
	 * This method initializes textFieldYawNeedleCartesianError	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextFieldYawNeedleCartesianError() {
		if (textFieldYawNeedleCartesianError == null) {
			textFieldYawNeedleCartesianError = new JTextField();
			textFieldYawNeedleCartesianError.setBounds(new Rectangle(526, 414, 137, 21));
			textFieldYawNeedleCartesianError.setEditable(false);
			textFieldYawNeedleCartesianError.setColumns(10);
			textFieldYawNeedleCartesianError.setText("0");
		}
		return textFieldYawNeedleCartesianError;
	}
	/**
	 * This method initializes jButtonRetractNeedle	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRetractNeedle() {
		if (jButtonRetractNeedle == null) {
			jButtonRetractNeedle = new JButton();
			jButtonRetractNeedle.setBounds(new Rectangle(358, 505, 120, 35));
			jButtonRetractNeedle.setText("Retract Needle");
		}
		return jButtonRetractNeedle;
	}
	/**
	 * This method initializes jButtonRetractStylet	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRetractStylet() {
		if (jButtonRetractStylet == null) {
			jButtonRetractStylet = new JButton();
			jButtonRetractStylet.setBounds(new Rectangle(520, 504, 120, 35));
			jButtonRetractStylet.setText("Retract Stylet");
		}
		return jButtonRetractStylet;
	}
	/**
	 * This method initializes jButtonHomePosition	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonHomePosition() {
		if (jButtonHomePosition == null) {
			jButtonHomePosition = new JButton();
			jButtonHomePosition.setBounds(new Rectangle(359, 463, 120, 35));
			jButtonHomePosition.setText("Home Position");
		}
		return jButtonHomePosition;
	}
	
	
	private JButton getJButtonZFrameRegistration() {
		if (jButtonZFrameRegistration == null) {
			jButtonZFrameRegistration = new JButton();
			jButtonZFrameRegistration.setBounds(new Rectangle(26, 161, 155, 27));
			jButtonZFrameRegistration.setText("Confirm Registration");
			//TODO list
		
			jButtonZFrameRegistration.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					tableZFrameRegistration.setEnabled(false);
				}
			});
//			jButtonStop.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					
//					System.out.println("Stopping all motors!");
//					getDevice().SetAllPIDSetPoint(getKinematicsGUI().getValues(), 0);
//				}
//			});
			
		}
		return jButtonZFrameRegistration;
	}

	

	public void setKinematicsGUI(KinematicsGUI kinematicsGUI) {
		this.kinematicsGUI = kinematicsGUI;
	}

	public GenericPIDDevice getDevice() {
		return kinematicsGUI.getDevice();
	}
	public KinematicsGUI getKinematicsGUI() {
		return kinematicsGUI;
	}
}
