package bda;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.swing.table.AbstractTableModel;

/**
 * Implementa um modelo de JTable adaptado à listagem de emails e posts
 * @author Grupo 29
 * @version 1.0
 */

@SuppressWarnings("serial")
public class BDATableModel extends AbstractTableModel {
	
	private Message[] messages;
	private String[] columnNames = new String[] { "Data e Hora", "Fonte", "Remetente", "Assunto" };

	/**
	 * Construtor que recebe uma lista de mensagens para apresentar
	 * @param msgs Mensagem a apresentar
	 */
	public BDATableModel(Message[] msgs) {
		messages = msgs;
	}

	/**
	 * Método que devolve o número de colunas
	 * @return Integer (número de colunas)
	 */
	@Override
	public int getColumnCount() {
		return 4;
	}

	/**
	 * Método que devolve o número de linhas
	 * @return Integer (número de linhas)
	 */
	@Override
	public int getRowCount() {
		return messages.length;
	}

	/**
	 * Método que devolve o valor de uma determinada célula
	 * @return Object (informação da mensagem contida na célula)
	 */
	@Override
	public Object getValueAt(int row, int column) {
		Message msg = messages[row];
		try {
			switch (column) {
			case 0:
				return msg.getReceivedDate();
			case 1:
				Address[] a = msg.getFrom();
				return ((InternetAddress) a[0]).getAddress();
			case 2:
				return "Gmail";
			case 3:
				return msg.getSubject();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Método que devolve uma String com o nome da coluna
	 * @return String (nome da coluna)
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}

}
