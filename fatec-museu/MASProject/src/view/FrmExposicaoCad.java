package view;

import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ExposicaoCtrl;

public class FrmExposicaoCad extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblIdExposio, lblTtuloDaExposio, lblDataInicio, lblDataFim, lblDescrio, lblArtista, lblTema;
	private JPanel contentPane;
	private JButton btnPesqArtista, btnCalInicial, btnCalFinal, btnGravar, btnLimpar;
	private JTextField txtID, txtTitulo, txtDataIni, txtDataFim, txtNomeArtista, txtTema;
	private JTable tableLista;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JScrollPane scrollPane;
	private JTextArea txtAreaDescri;
	private JScrollPane scrollPane_1;
	private MaskFormatter maskData;

	public static void main(String[] args) {

		try {
			new FrmExposicaoCad().setVisible(true);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmExposicaoCad() throws ParseException {
		setResizable(false);
		setTitle("Nova Exposi\u00E7\u00E3o");
		FrmExp();
		setLocation(0,0);
	}

	public void FrmExp() throws ParseException {
		setClosable(true);
		setIconifiable(true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 937, 680);
		contentPane = new JPanel();
		contentPane.setName("EXP");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblIdExposio = new JLabel("ID. Exposi\u00E7\u00E3o");
		lblIdExposio.setBounds(56, 30, 78, 14);
		contentPane.add(lblIdExposio);

		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setBounds(157, 27, 226, 20);
		txtID.setEditable(false);
		contentPane.add(txtID);
		txtID.setColumns(10);

		txtTitulo = new JTextField();
		txtTitulo.setBounds(157, 58, 301, 20);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);

		lblTtuloDaExposio = new JLabel("T\u00EDtulo da Exposi\u00E7\u00E3o");
		lblTtuloDaExposio.setBounds(33, 61, 114, 14);
		contentPane.add(lblTtuloDaExposio);

		maskData = new MaskFormatter("##/##/####");

		txtDataIni = new JFormattedTextField(maskData);
		txtDataIni.setBounds(157, 110, 86, 20);
		contentPane.add(txtDataIni);
		txtDataIni.setColumns(10);

		lblDataInicio = new JLabel("Data Inicio");
		lblDataInicio.setBounds(73, 113, 61, 14);
		contentPane.add(lblDataInicio);

		lblDataFim = new JLabel("Data Fim");
		lblDataFim.setBounds(319, 113, 61, 14);
		contentPane.add(lblDataFim);

		txtDataFim = new JFormattedTextField(maskData);
		txtDataFim.setBounds(373, 110, 86, 20);
		contentPane.add(txtDataFim);
		txtDataFim.setColumns(10);

		lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(73, 222, 61, 14);
		contentPane.add(lblDescrio);

		lblArtista = new JLabel("Artista");
		lblArtista.setBounds(88, 352, 46, 14);
		contentPane.add(lblArtista);

		txtNomeArtista = new JTextField("Digite o nome do artista");
		txtNomeArtista.setBounds(157, 349, 262, 20);
		contentPane.add(txtNomeArtista);
		txtNomeArtista.setColumns(10);

		btnPesqArtista = new JButton("");
		btnPesqArtista.setBounds(429, 346, 29, 23);
		btnPesqArtista.setIcon(new ImageIcon("../MASProject/icons/search.png"));
		contentPane.add(btnPesqArtista);

		txtTema = new JTextField();
		txtTema.setBounds(157, 156, 303, 20);
		contentPane.add(txtTema);
		txtTema.setColumns(10);

		lblTema = new JLabel("Tema");
		lblTema.setBounds(97, 159, 37, 14);
		contentPane.add(lblTema);
		
		btnCalInicial = new JButton("");
		btnCalInicial.setBounds(255, 109, 29, 23);
		btnCalInicial.setIcon(new ImageIcon("../MASProject/jcalendar-1.4 (1)/src/com/toedter/calendar/images/JDateChooserColor32.gif"));
		contentPane.add(btnCalInicial);
		
		btnCalFinal = new JButton("");
		btnCalFinal.setBounds(471, 109, 29, 23);
		btnCalFinal.setIcon(new ImageIcon("../MASProject/jcalendar-1.4 (1)/src/com/toedter/calendar/images/JDateChooserColor32.gif"));
		contentPane.add(btnCalFinal);

		btnGravar = new JButton("Gravar");
		btnGravar.setIcon(new ImageIcon("../MASProject/icons/save.png"));
		btnGravar.setBounds(756, 607, 108, 34);
		contentPane.add(btnGravar);

		btnLimpar = new JButton("Limpar");
		btnLimpar.setIcon(new ImageIcon("../MASProject/icons/clear.png"));
		btnLimpar.setBounds(622, 607, 108, 34);
		contentPane.add(btnLimpar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(157, 413, 707, 166);
		contentPane.add(scrollPane);

		tableLista = new JTable(tableModel);
		scrollPane.setViewportView(tableLista);
		tableLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableLista.setSelectionBackground(Color.LIGHT_GRAY);
		tableLista.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tableLista.setColumnSelectionAllowed(false);
		tableLista.setCellSelectionEnabled(false);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(157, 222, 707, 87);
		contentPane.add(scrollPane_1);

		txtAreaDescri = new JTextArea();
		scrollPane_1.setViewportView(txtAreaDescri);

		
		ExposicaoCtrl expCtrl = new ExposicaoCtrl(contentPane, txtDataIni, 
				txtDataFim, txtNomeArtista, txtID, tableLista,
				txtTitulo,txtTema,txtAreaDescri, tableModel, btnPesqArtista);
		btnCalInicial.addActionListener(expCtrl.abreCalInicial);
		btnCalFinal.addActionListener(expCtrl.abreCalFinal);
		btnGravar.addActionListener(expCtrl.gravarExpo);
		btnLimpar.addActionListener(expCtrl.limpar);

		expCtrl.gerarId();
		tableLista.addKeyListener(expCtrl);
		btnPesqArtista.addActionListener(expCtrl);
		txtNomeArtista.addKeyListener(expCtrl);
		

	}
}
