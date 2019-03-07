package songbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PageLabelNumberingStyle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import songbook.data.Album;
import songbook.data.Artist;
import songbook.data.Song;
import songbook.data.Songbook;

/**
 * Created by pwilkin on 13-Dec-18.
 */
public class DataSaver {

    public void exportSongbookToPdf(Songbook songbook, PdfDocument pdfDocument) throws IOException {
        try (
        		Document document = new Document(pdfDocument);

        		) {
        	
        	//title

        	//Color color = new Color(new PdfColorSpace(new PdfObject(), 1));
    
        	Paragraph first = new Paragraph();
        	first.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD));
        	Text songbookTitle = new Text("The Songbook of Janek");
        	songbookTitle.setFontSize(40);
        	songbookTitle.setBold().setUnderline();
        	songbookTitle.setHorizontalAlignment(HorizontalAlignment.CENTER); //jakos nie dziala
        	first.add(songbookTitle);
        	first.add(new Text("\n \n \n \n"));

        	//table of contents
        	first.add(new Text("Table of contents:").setFontSize(20).setBold().setUnderline());
        	for (Artist artist : songbook.getArtists()) {
        		first.add(new Text("\n Artist: " + artist.getName()).setBold().setFontSize(17));
        		for (Album album : artist.getAlbums()) {
        			first.add(new Text("\n Album: " + album.getName()).setFontSize(13));
        			for (Song song : album.getSongs()) {
        				first.add(new Text("\n - " + song.getTitle() + " ----- p. ").setItalic());
        	}}}
        	
        	document.add(first);
        	
        	ArrayList<Integer> contents = new ArrayList(); //songs' pages' positions: index=song number, value=song position
        	int songNo = 0;
        	
        	
        	//pdfDocument.getPage(1).setPageLabel(PageLabelNumberingStyle.DECIMAL_ARABIC_NUMERALS, "aaaaa");
        	//document.add(new AreaBreak());
        	//pdfDocument.addPage(pdfDocument.getPage(1).setPageLabel(PageLabelNumberingStyle.DECIMAL_ARABIC_NUMERALS, "aaaaa"));
        	//tu cos skonczylem
        	
            document.add(new AreaBreak());
            
            int pages = pdfDocument.getNumberOfPages();
            //System.out.println(pages);
            
            for (int i = 1; i <= pages; i++) {
            	document.showTextAligned(new Paragraph(String.format("page %s", i)), 559, 806, i, 
            			TextAlignment.RIGHT, VerticalAlignment.TOP, 0 );
            }
            int lastpage = pages; //last page with numbers already given        	
        	
        	
            for (Artist artist : songbook.getArtists()) {
            	Paragraph artistPara = new Paragraph("Artist: " + artist.getName());
            	artistPara.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD));
                artistPara.setFontSize(22);
            	document.add(artistPara);
            	
            	for (Album album : artist.getAlbums()) {
            		Paragraph albumPara = new Paragraph("Album: " + album.getName());
            		albumPara.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
            		albumPara.setFontSize(17);
            		document.add(albumPara);
            		
                    for (Song song : album.getSongs()) {
                        String title = song.getTitle();
                        Paragraph songPara = new Paragraph();
                        songPara.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
                        songPara.add(new Text("\"" + title + "\"").setFontSize(14));
                        document.add(songPara);
                        
                        Paragraph lyricsPara = new Paragraph(song.getLyrics());
                        lyricsPara.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA)).setFontSize(12);
                        document.add(lyricsPara);
                        
                        contents.add(pages+1);
                        pages = pdfDocument.getNumberOfPages();
                        for (int i = lastpage+1; i <= pages; i++) {
                        	document.showTextAligned(new Paragraph(String.format("page %s", i)), 559, 806, i, 
                        			TextAlignment.RIGHT, VerticalAlignment.TOP, 0 );
                        };
                        System.out.print(lastpage+1);
                        lastpage = pages;
                        
                        document.add(new AreaBreak());
            }}}
            System.out.println("abababab");
            for (int j : contents)
            	System.out.println(j);
            
           /*pages = pdfDocument.getNumberOfPages();
           for (int i = 1; i <= pages; i++) {
        	
        	document.showTextAligned(new Paragraph(String.format("page %s of %s", 1, 2)), 559, 806, i, 
        			TextAlignment.RIGHT, VerticalAlignment.TOP, 0 );
        	
        }
            **/
            
            //adding page nummeration
            //PageLabelNumberingStyle plns = new PageLabelNumberingStyle();
            //pdfDocument.getPage(1).setPageLabel(PageLabelNumberingStyle.DECIMAL_ARABIC_NUMERALS, null);
            //pdfDocument.getPage(2).setPage
            //pdfDocumen
            //pdfDocument.addPage(pdfDocument.getPage(1).setPageLabel(PageLabelNumberingStyle.DECIMAL_ARABIC_NUMERALS, null));

            
            
            
            //PdfDocument pdfDoc = new PdfDocument(new PdfReader())
            
            
            //int n = pdfDocument.getNumberOfPages();
            //System.out.println(n);
            int n = 5;
            //for (int i = 0; i <= n; i++) {
            //	document.showTextAligned(new Paragraph(String.format("page %s of %s", i, n)), 559, 806, i, 
            //			TextAlignment.RIGHT, VerticalAlignment.TOP, 0 );
            //}
            
        }
    }

    public static class DataException extends Exception {
        public DataException(String reason) {
            super(reason);
        }
    }

    public void writeSongbook(Songbook songbook, OutputStream os) {
        StringBuilder sb = new StringBuilder();
        for (Artist artist : songbook.getArtists()) {
            sb.append("###\n").append(artist.getName()).append("\n");
            for (Album album : artist.getAlbums()) {
                sb.append("##\n").append(album.getName()).append("\n");
                for (Song song : album.getSongs()) {
                    sb.append("#\n").append(song.getTitle()).append("#").append(
                        Arrays.stream(song.getLyrics().split("\n")).map(x -> x.replaceAll("#", "\\#"))
                            .collect(Collectors.joining("#"))
                    ).append("\n");
                }
            }
        }
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
        pw.print(sb.toString());
        pw.flush();
    }

    public Songbook readSongbook(InputStream is) throws DataException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        Songbook book = new Songbook();
        book.setArtists(new ArrayList<>());
        String line;
        boolean readingArtistName = false;
        boolean readingAlbumName = false;
        boolean readingSong = false;
        Artist currentArtist = null;
        Album currentAlbum = null;
        try {
            int i = 1;
            while ((line = bf.readLine()) != null) {
                // line = line.trim(); - naprawiamy linie ze spacjami na końcu
                if (readingArtistName) {
                    currentArtist = new Artist();
                    currentArtist.setName(line);
                    currentArtist.setAlbums(new ArrayList<>());
                    book.getArtists().add(currentArtist);
                    readingArtistName = false;
                } else if (readingAlbumName) {
                    currentAlbum = new Album();
                    currentAlbum.setName(line);
                    currentAlbum.setSongs(new ArrayList<>());
                    currentArtist.getAlbums().add(currentAlbum);
                    readingAlbumName = false;
                } else if (readingSong) {
                    currentAlbum.getSongs().add(readSong(line));
                    readingSong = false;
                } else {
                    if ("###".equals(line)) {
                        readingArtistName = true;
                    } else if ("##".equals(line)) {
                        readingAlbumName = true;
                    } else if ("#".equals(line)) {
                        readingSong = true;
                    } else {
                        throw new DataException("Error in line " + i + ", expected ###, ## or #, got \"" + line + "\"");
                    }
                }
                i++;
            }
        } catch (IOException e) {
            throw new DataException("Error reading from file: " + e.getMessage());
        }
        return book;
    }

    private Song readSong(String line) {
        boolean readingTitle = true;
        boolean inEscape = false;
        StringBuilder current = new StringBuilder();
        Song song = new Song();
        for (int i = 0; i < line.length(); i++) {
            String cur = line.substring(i, i + 1);
            if (inEscape) {
                current.append(cur);
                inEscape = false;
            } else {
                if ("\\".equals(cur)) {
                    inEscape = true;
                } else if ("#".equals(cur)) {
                    if (readingTitle) {
                        song.setTitle(current.toString());
                        song.setLyrics("");
                        readingTitle = false;
                    } else {
                        song.setLyrics(song.getLyrics() + current.toString() + "\n");
                    }
                    current = new StringBuilder();
                } else {
                    current.append(cur);
                }
            }
        }
        song.setLyrics(song.getLyrics() + current.toString());
        return song;
    }

}
