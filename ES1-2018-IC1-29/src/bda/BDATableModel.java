package bda;

import java.io.IOException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.swing.table.AbstractTableModel;


/**
 * Implementa um modelo de JTable adaptado à listagem de emails e posts
 * 
 * @author Grupo 29
 * @version 1.0
 */

@SuppressWarnings("serial")
public class BDATableModel extends AbstractTableModel {

	private ArrayList<Content> content;
	private String[] columnNames = new String[] { "Data e Hora", "Fonte", "Remetente", "Assunto" };

	/**
	 * Construtor que recebe uma lista de mensagens para apresentar
	 * 
	 * @param msgs Mensagem a apresentar
	 */
	public BDATableModel(ArrayList<Content> content) {
		this.content = content;
	}

	/**
	 * Método que devolve o número de colunas
	 * 
	 * @return Integer (número de colunas)
	 */
	@Override
	public int getColumnCount() {
		return 4;
	}

	/**
	 * Método que devolve o número de linhas
	 * 
	 * @return Integer (número de linhas)
	 */
	@Override
	public int getRowCount() {
		return content.size();
	}

	/**
	 * Método que devolve o valor de uma determinada célula
	 * 
	 * @return Object (informação da mensagem contida na célula)
	 */
	@Override
	public Object getValueAt(int row, int column) {
		Content c = content.get(row);
		try {
			switch (column) {
			case 0:
				return c.getDate();
			case 1:
				return c.getFrom();
			case 2:
				return c.getType();
			case 3:
				return c.getSubject();
			}
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Método que devolve uma String com o nome da coluna
	 * 
	 * @return Message (de uma determinada coluna)
	 * @throws MessagingException
	 * @throws IOException
	 */

	public Content getMessageAt(int row) throws IOException, MessagingException {
		return content.get(row);

	}

	/**
	 * Método que devolve uma String com o nome da coluna
	 * 
	 * @return String (nome da coluna)
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public void addRow(Content c) {
		content.add(c);
	}

	public ArrayList<Content> getTableContent() {
		return content;
	}

}
