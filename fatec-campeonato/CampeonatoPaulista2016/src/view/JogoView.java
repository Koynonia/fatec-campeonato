/**
 * @author Fernando Moraes Oliveira
 * Matéria Laboratório de Banco de Dados
 * 5º ADS - Tarde
 * Iniciado em 25/09/2016
 */

package view;

import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.text.MaskFormatter;

import controller.JogoCtrl;

public class JogoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1974202157363751649L;
	private JPanel contentPane;
	private JTable tabJogos;
	private JFormattedTextField ftxtData;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JogoView frame = new JogoView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JogoView() {
		
		setBounds(100, 100, 826, 480);
		setTitle("Jogos do Campeonato");
		setName("Jogos");
		setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblData = new JLabel("Início do Campeonato:");
		lblData.setBounds(53, 37, 164, 16);
		contentPane.add(lblData);
		
		MaskFormatter maskData;
		try {
			maskData = new MaskFormatter("##/##/####");
			ftxtData = new JFormattedTextField(maskData);
			ftxtData.setHorizontalAlignment(SwingConstants.CENTER);
			ftxtData.setBounds(223, 31, 134, 29);
			contentPane.add(ftxtData);
			ftxtData.setColumns(10);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel lblJogos = new JLabel("Jogos do Campeonato:");
		lblJogos.setBounds(53, 95, 722, 16);
		contentPane.add(lblJogos);
		
		tabJogos = new JTable();
		tabJogos.setBorder(null);
		tabJogos.setAutoCreateRowSorter(true);
		
		JScrollPane spJogos = new JScrollPane();
		spJogos.setBounds(53, 119, 722, 194);
		spJogos.setViewportView(tabJogos);
		contentPane.add(spJogos);
		
		JButton btnGerar = new JButton("Gerar Jogos");
		btnGerar.setBounds(369, 32, 117, 29);
		btnGerar.setVisible(true);
		contentPane.add(btnGerar);
		
		JButton btnVerificar = new JButton("Verificar");
		btnVerificar.setBounds(369, 32, 117, 29);
		btnVerificar.setVisible(false);
		contentPane.add(btnVerificar);
		
		JButton btnApagar = new JButton("Apagar Jogos");
		btnApagar.setBounds(519, 396, 126, 29);
		contentPane.add(btnApagar);
		
		JButton btnGol = new JButton("Gerar Gols");
		btnGol.setBounds(381, 396, 126, 29);
		contentPane.add(btnGol);
		
		JButton btnFechar = new JButton("Voltar");
		btnFechar.setBounds(657, 396, 117, 29);
		contentPane.add(btnFechar);
		
		JRadioButton rdbtnTrigger = new JRadioButton("Ativar Trigger ( INSERT, DELETE )");
		rdbtnTrigger.setBounds(53, 325, 302, 23);
		contentPane.add(rdbtnTrigger);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(JogoView.class.getResource("/resources/back.jpg")));
		lblBackground.setBounds(0, 0, 826, 458);
		contentPane.add(lblBackground);
		
		JogoCtrl j = new JogoCtrl( this, lblData, lblJogos, rdbtnTrigger, btnVerificar, btnGerar, btnApagar, ftxtData, tabJogos );
		
		btnVerificar.addActionListener(j.data);
		btnGerar.addActionListener(j.preencherTabela);
		btnGol.addActionListener(j.gerarGols);
		btnApagar.addActionListener(j.apagar);
		btnFechar.addActionListener(j.fechar);
	}
}
