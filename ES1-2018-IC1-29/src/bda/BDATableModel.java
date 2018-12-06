package bda;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;
import javax.swing.table.AbstractTableModel;

/**
 * Implementa um modelo de JTable adaptado à listagem de emails, tweets e posts
 * 
 * @author Grupo 29
 * @version 2.0
 */

@SuppressWarnings("serial")
public class BDATableModel extends AbstractTableModel {

	private ArrayList<Content> content;
	private ArrayList<Content> backupContent = new ArrayList<>();
	private ArrayList<Content> filteredContent = new ArrayList<>();
	private String[] columnNames = new String[] { "Data e Hora", "Fonte", "Remetente", "Assunto" };
	private ArrayList<Object> contentHandlers = new ArrayList<>();
	private DateComparator dc = new DateComparator();

	/**
	 * Construtor que recebe uma lista de conteúdos para apresentar
	 * 
	 * @param content ArrayList<Content> a apresentar
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
				return c.getType();
			case 2:
				return c.getFrom();
			case 3:
				return c.getSubject();
			}
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void filter(String text, boolean from, boolean type, boolean subject, boolean time) {

		if (backupContent.isEmpty())
			backupContent.addAll(content);

		filteredContent.clear();
		content.clear();
		// sem filtro
		if (!from && !type && !subject && !time) {
			content.addAll(backupContent);
			fireTableDataChanged();
			return;
			// com filtro
		} else {
			try {
				for (Content c : backupContent) {
					boolean add = false;
					if (from) {
						if (c.getFrom().toLowerCase().contains(text.toLowerCase()))
							add = true;
					}

					if (type) {
						if (c.getType().toLowerCase().contains(text.toLowerCase()))
							add = true;
					}
					if (subject) {
						if (c.getSubject().toLowerCase().contains(text.toLowerCase()))
							add = true;
					}
					if (time) {
						if (!dc.moreThanDay(c.getDate()))
							add = true;
					}

					if (add)
						filteredContent.add(c);

				}
			} catch (IOException | MessagingException e) {
				e.printStackTrace();
			}
		}

		content.addAll(filteredContent);
		fireTableDataChanged();

	}

	/**
	 * Método que devolve uma String com o nome da coluna
	 * 
	 * @return Content (de uma determinada coluna)
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

	/**
	 * Método que adiciona uma linha à lista de conteúdos
	 * 
	 * @param c Content
	 */
	public void addRow(Content c) {
		content.add(c);
	}

	/**
	 * Método que retorna um arraylist com o conteúdo da tabela
	 * 
	 * @return content ArrayList<Content>
	 */
	public ArrayList<Content> getTableContent() {
		return content;
	}

	public void addContentHandler(Object cH) {
		contentHandlers.add(cH);

	}

	public ArrayList<Object> getContentHandlers() {
		return contentHandlers;
	}

}
