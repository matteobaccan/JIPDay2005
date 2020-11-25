/*
 * @(#)Test10bis.java      1.00 30/09/2005
 *
 *  Copyright (C) 2005  Leonardo Badini <leonardo.badini@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Licence details at http://www.gnu.org/licenses/gpl.txt
 */

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.tv.xlet.*;
import org.havi.ui.*;

public class Test10bis implements Xlet, KeyListener
{
   private XletContext context;
   private HScene scene;
   private HContainer cont;
   private Font font;
   private HDefaultTextLayoutManager tlm;
   private HText text1;
   private HText text2;
   private HText text3;
   private HText text3bis;
   private HText text4;
   private Image image;
   private HGraphicButton Botton;
   private HGraphicButton Botton1;
   private HGraphicButton Botton2;

   private HText tcesci;
   private HText tesci;
   private HText tqueryerr;
   private HText tconf;
   private HText tnonimm;
   private HText terut;
   private HText terlib;
   private HContainer cesci;
   private HContainer cqueryerr;
   private HContainer cconf;
   private HContainer cnonimm;
   private HContainer cerut;
   private HContainer cerlib;

   private JLabel [] informazione;

   private JLabel lesci = new JLabel("Sei sicuro di voler uscire?");
   private JLabel l1esci = new JLabel("Premi DESTRA/SINISTRA per scegliere");
   private JLabel l2esci = new JLabel("Premi OK per confermare");
   private JLabel l3esci = new JLabel("Premi OK per confermare");
   private JLabel l4esci = new JLabel("Premi OK per confermare");
   private JLabel l5esci = new JLabel("Premi OK per confermare");
   private JLabel l6esci = new JLabel("Premi DESTRA/SINISTRA per scegliere");
   private JLabel l7esci = new JLabel("Premi OK per confermare");
   private JButton besci = new JButton("Si");
   private JButton bnonesci = new JButton("No");
   private JLabel queryErrata = new JLabel("La ricerca non ha prodotto risultati");
   private JLabel queryErrata2 = new JLabel("Premi il tasto");
   private JLabel lrossa1 = new JLabel("ROSSO");
   private JLabel queryErrata3 = new JLabel("per ripetere la ricerca");
   private JLabel conferma = new JLabel("Confermi la richiesta di prenotazione?");
   private JButton bsi = new JButton("Si");
   private JButton bno = new JButton("No");
   private JLabel lnonimm = new JLabel("Codice utente o codice libro non immesso");
   private JButton bnonimm = new JButton("Ok");
   private JLabel errutente = new JLabel("Codice utente inserito errato o non presente");
   private JButton erbut = new JButton("Ok");
   private JLabel errlibro = new JLabel("Codice libro inserito errato o non presente");
   private JButton erblib = new JButton("Ok");
   private JButton iniziale = new JButton("Torna alla pagina iniziale");

   private int statoGenerale;
   private int statoRosso;
   private int statoTastiera;
   private int statoCombo;
   private int statoBiblio;
   private int gen;
   private int red;

   private boolean isUp;

   private ArrayList valori = new ArrayList(18);
   private ArrayList label = new ArrayList(18);
   private ArrayList cod = new ArrayList(20);

   private JLabel label1 = new JLabel("Autore:");
   private JTextField tf1 = new JTextField(20);
   private JLabel label2 = new JLabel("Titolo:");
   private JTextField tf2 = new JTextField(20);
   private JLabel label3 = new JLabel("Biblioteca:");
   private JComboBox combo;
   private JButton button1 = new JButton("Invia");
   private JButton [] tasto;
   private String [] biblio = {"Milano","Firenze","Roma","Napoli","Palermo"};
   private JLabel lutente = new JLabel("Codice Utente");
   private JTextField tutente = new JTextField(20);
   private JLabel llibro = new JLabel("Codice Libro");
   private JTextField tlibro = new JTextField(20);
   private JButton bquery = new JButton("Ok");

   private MatteBorder matte = new MatteBorder(5, 5, 5, 5, Color.red);
   private LineBorder line = new LineBorder(Color.black);

   private boolean rutente;
   private boolean rlibro;
   public Test10bis(){}
   public void initXlet(XletContext context) throws XletStateChangeException
   {
      this.context = context;
      System.out.println(this.getClass().getName()+" : initXlet");
      HSceneFactory hsf = HSceneFactory.getInstance();
      scene = hsf.getFullScreenScene(HScreen.getDefaultHScreen().getDefaultHGraphicsDevice());
      scene.setSize(1024,768);
      scene.setLayout(null);
      scene.addKeyListener(this);
      font = new Font("Tiresias", Font.PLAIN, 20);
      tlm = new HDefaultTextLayoutManager();
      cont = new HContainer(10, 10, 710, 566);
      image =Toolkit.getDefaultToolkit().getImage("logoblk1.jpg");
      Botton = new HGraphicButton(image,0,0,150,80);
      cont.add(Botton);
      text1 = new HText(null, 0, 0, 700, 80, font, Color.white, Color.black, tlm);
      text2 = new HText(null, 0, 80, 700, 440, font, Color.black, Color.white, tlm);
      text3 = new HText(null, 0, 500, 700, 56, font, Color.white, Color.blue, tlm);
      text3bis = new HText(null, 350, 500, 350, 56, font, Color.white, Color.blue, tlm);
      text4 = new HText(null, 0, 500, 350, 56, font, Color.white, Color.yellow, tlm);

      cesci = new HContainer(405,150,270,180);
      cqueryerr = new HContainer(405,150,270,180);
      cconf = new HContainer(405,150,270,180);
      cerut = new HContainer(405,150,270,180);
      cerlib = new HContainer(405,150,270,180);
      cnonimm = new HContainer(405,150,270,180);
      tcesci = new HText(null,0,0,270,180,font,Color.black,Color.blue,tlm);
      tesci = new HText(null,5,20,260,155,font,Color.black,Color.white,tlm);
      tqueryerr = new HText(null,0,0,270,180,font,Color.white,Color.black,tlm);
      tconf = new HText(null,0,0,270,180,font,Color.white,Color.black,tlm);
      terut = new HText(null,0,0,270,180,font,Color.white,Color.black,tlm);
      terlib = new HText(null,0,0,270,180,font,Color.white,Color.black,tlm);
      tnonimm = new HText(null,0,0,270,180,font,Color.white,Color.black,tlm);

      statoGenerale = 0;
      statoRosso = 0;
      statoTastiera = 14;
      statoCombo = 0;
      statoBiblio = 0;
      isUp = true;
      rutente = false;
      rlibro = false;
      label1.setBounds(60, 110, 50, 20);
      cont.add(label1);
      tf1.setBounds(170, 110, 180, 25);
      tf1.setBackground(Color.lightGray);
      tf1.setBorder(matte);
      cont.add(tf1);
      label2.setBounds(60, 175, 60, 20);
      cont.add(label2);
      tf2.setBounds(170, 175, 180, 25);
      tf2.setBackground(Color.lightGray);
      tf2.setBorder(line);
      cont.add(tf2);
      label3.setBounds(60, 240, 60, 20);
      cont.add(label3);
      combo = new JComboBox(biblio);
      combo.setBounds(170, 240, 150, 27);
      combo.setSelectedItem(biblio[0]);
      combo.setBackground(Color.lightGray);
      combo.setBorder(line);
      cont.add(combo);
      button1.setBounds(170,305,135,27);
      button1.setBackground(Color.lightGray);
      button1.setBorder(line);
      cont.add(button1);

      informazione = new JLabel[32];
      informazione[0] = new JLabel("Stai compilando il campo Autore");
      informazione[1] = new JLabel("Stai compilando il campo Titolo");
      informazione[2] = new JLabel("Stai compilando il campo Biblioteca");
      informazione[3] = new JLabel("Usa i tasti cursore ed il tasto OK per selezionare le lettere sulla tastiera");
      informazione[4] = new JLabel("Premi il tasto");
      informazione[5] = new JLabel("ROSSO");
      informazione[6] = new JLabel("Stai inviando il modulo");
      informazione[7] = new JLabel("Usa il tasto Ok per inoltrare la ricerca");
      informazione[8] = new JLabel("per passare al campo successivo,");
      informazione[9] = new JLabel("Premi il tasto BLU per uscire dall'applicazione");
      informazione[10] = new JLabel("Exit");
      informazione[11] = new JLabel("Usa i tasti SU/GIU' e il tasto OK per selezionare la biblioteca nella lista");
      informazione[12] = new JLabel("Stai compilando il campo Codice Utente");
      informazione[13] = new JLabel("Stai compilando il campo Codice Libro");
      informazione[14] = new JLabel("Usa i tasti numerici per inserire il codice, premi SINISTRA per cancellare");
      informazione[15] = new JLabel("Premi il tasto GIALLO per tornare indietro");
      informazione[16] = new JLabel("Premi il tasto BLU per uscire dall'applicazione");
      informazione[17] = new JLabel("VERDE");
      informazione[18] = new JLabel("per passare ad Invio");
      informazione[19] = new JLabel("VERDE");
      informazione[20] = new JLabel("per passare al precedente");
      informazione[21] = new JLabel("per tornare ad Autore,");
      informazione[22] = new JLabel("per passare al campo precedente");
      informazione[23] = new JLabel("Premi il tasto");
      informazione[24] = new JLabel("ROSSO");
      informazione[25] = new JLabel("per passare al campo successivo,");
      informazione[26] = new JLabel("VERDE");
      informazione[27] = new JLabel("per passare ad Invio");
      informazione[28] = new JLabel("per passare al precedente");
      informazione[29] = new JLabel("per tornare al Codice Utente,");
      informazione[30] = new JLabel("VERDE");
      informazione[31] = new JLabel("per passare al campo precedente");

      for(int i = 0; i < informazione.length; i++)
         informazione[i].setForeground(Color.white);
      informazione[15].setForeground(Color.black);
      informazione[5].setForeground(Color.red);
      informazione[17].setForeground(Color.green);
      informazione[19].setForeground(Color.green);
      informazione[24].setForeground(Color.red);
      informazione[26].setForeground(Color.green);
      informazione[30].setForeground(Color.green);

      informazione[0].setBounds(150,10,700,18);
      informazione[3].setBounds(150,30,700,18);
      informazione[4].setBounds(150,50,200,18);
      informazione[5].setBounds(226,50,200,18);
      informazione[8].setBounds(271,50,200,18);
      informazione[17].setBounds(464,50,200,18);
      informazione[18].setBounds(508,50,200,18);
      informazione[9].setBounds(225,528,700,18);
      informazione[10].setBounds(631,528,100,18);
      cont.add(informazione[0]);
      cont.add(informazione[3]);
      cont.add(informazione[4]);
      cont.add(informazione[5]);
      cont.add(informazione[8]);
      cont.add(informazione[17]);
      cont.add(informazione[18]);
      cont.add(informazione[9]);
      cont.add(informazione[10]);
      informazione[1].setBounds(150,10,700,18);
      informazione[2].setBounds(150,10,700,18);
      informazione[6].setBounds(150,10,700,18);
      informazione[7].setBounds(150,30,700,18);
      informazione[11].setBounds(150,30,700,18);
      informazione[12].setBounds(150,10,700,18);
      informazione[13].setBounds(150,10,700,18);
      informazione[14].setBounds(150,30,700,18);
      informazione[15].setBounds(72,529,700,18);
      informazione[16].setBounds(394,529,700,18);
      informazione[19].setBounds(395,50,200,18);
      informazione[20].setBounds(508,50,200,18);
      informazione[21].setBounds(271,50,200,18);
      informazione[22].setBounds(439,50,200,18);
      informazione[23].setBounds(150,50,200,18);
      informazione[24].setBounds(226,50,200,18);
      informazione[25].setBounds(271,50,200,18);
      informazione[26].setBounds(464,50,200,18);
      informazione[27].setBounds(508,50,200,18);
      informazione[28].setBounds(508,50,200,18);
      informazione[29].setBounds(271,50,200,18);
      informazione[30].setBounds(428,50,200,18);
      informazione[31].setBounds(472,50,200,18);

      informazione[1].setVisible(false);
      informazione[2].setVisible(false);
      informazione[6].setVisible(false);
      informazione[7].setVisible(false);
      informazione[11].setVisible(false);
      informazione[19].setVisible(false);
      informazione[20].setVisible(false);
      informazione[21].setVisible(false);
      informazione[22].setVisible(false);

      cont.add(informazione[1]);
      cont.add(informazione[2]);
      cont.add(informazione[6]);
      cont.add(informazione[7]);
      cont.add(informazione[11]);
      cont.add(informazione[19]);
      cont.add(informazione[20]);
      cont.add(informazione[21]);
      cont.add(informazione[22]);

      tasto = new JButton[60];
      tasto[0] = new JButton("Q");
      tasto[1] = new JButton("W");
      tasto[2] = new JButton("E");
      tasto[3] = new JButton("R");
      tasto[4] = new JButton("T");
      tasto[5] = new JButton("Y");
      tasto[6] = new JButton("U");
      tasto[7] = new JButton("I");
      tasto[8] = new JButton("O");
      tasto[9] = new JButton("P");
      tasto[10] = new JButton("A");
      tasto[11] = new JButton("S");
      tasto[12] = new JButton("D");
      tasto[13] = new JButton("F");
      tasto[14] = new JButton("G");
      tasto[15] = new JButton("H");
      tasto[16] = new JButton("J");
      tasto[17] = new JButton("K");
      tasto[18] = new JButton("L");
      tasto[19] = new JButton("'");
      tasto[20] = new JButton("Dw");
      tasto[21] = new JButton("Z");
      tasto[22] = new JButton("X");
      tasto[23] = new JButton("C");
      tasto[24] = new JButton("V");
      tasto[25] = new JButton("B");
      tasto[26] = new JButton("N");
      tasto[27] = new JButton("M");
      tasto[28] = new JButton("Sp");
      tasto[29] = new JButton("Cl");
      tasto[30] = new JButton("q");
      tasto[31] = new JButton("w");
      tasto[32] = new JButton("e");
      tasto[33] = new JButton("r");
      tasto[34] = new JButton("t");
      tasto[35] = new JButton("y");
      tasto[36] = new JButton("u");
      tasto[37] = new JButton("i");
      tasto[38] = new JButton("o");
      tasto[39] = new JButton("p");
      tasto[40] = new JButton("a");
      tasto[41] = new JButton("s");
      tasto[42] = new JButton("d");
      tasto[43] = new JButton("f");
      tasto[44] = new JButton("g");
      tasto[45] = new JButton("h");
      tasto[46] = new JButton("j");
      tasto[47] = new JButton("k");
      tasto[48] = new JButton("l");
      tasto[49] = new JButton("'");
      tasto[50] = new JButton("Up");
      tasto[51] = new JButton("z");
      tasto[52] = new JButton("x");
      tasto[53] = new JButton("c");
      tasto[54] = new JButton("v");
      tasto[55] = new JButton("b");
      tasto[56] = new JButton("n");
      tasto[57] = new JButton("m");
      tasto[58] = new JButton("Sp");
      tasto[59] = new JButton("Cl");
      for(int i = 0; i < tasto.length; i++)
         tasto[i].setBackground(Color.lightGray);
      int x1 = 100;
      for(int i = 0; i < 10; i++)
      {
         tasto[i].setBounds(x1,365,50,45);
         x1 += 50;
      }
      x1 = 100;
      for(int i = 10; i < 20; i++)
      {
         tasto[i].setBounds(x1,410,50,45);
         x1 += 50;
      }
      x1 = 100;
      for(int i = 20; i < 30; i++)
      {
         tasto[i].setBounds(x1,455,50,45);
         x1 += 50;
      }
      x1 = 100;
      for(int i = 30; i < 40; i++)
      {
         tasto[i].setBounds(x1,365,50,45);
         x1 += 50;
      }
      x1 = 100;
      for(int i = 40; i < 50; i++)
      {
         tasto[i].setBounds(x1,410,50,45);
         x1 += 50;
      }
      x1 = 100;
      for(int i = 50; i < 60; i++)
      {
         tasto[i].setBounds(x1,455,50,45);
         x1 += 50;
      }
      for(int i = 0; i < 30; i++)
         cont.add(tasto[i]);
      tasto[14].setBackground(Color.black);
      tasto[14].setForeground(Color.white);

      fillEsci();
      fillQueryerr();
      fillNonimm();
      fillErlib();
      fillErut();
      fillConferma();

      cont.add(text1);
      cont.add(text2);
      cont.add(text3);
      cesci.setVisible(false);
      cqueryerr.setVisible(false);
      cconf.setVisible(false);
      cnonimm.setVisible(false);
      cerut.setVisible(false);
      cerlib.setVisible(false);
      scene.add(cesci);
      scene.add(cqueryerr);
      scene.add(cnonimm);
      scene.add(cerlib);
      scene.add(cerut);
      scene.add(cconf);
      scene.add(cont);
      scene.setVisible(true);
      scene.requestFocus();
      scene.repaint();
   }

   public void startXlet() throws XletStateChangeException
   {
      System.out.println(this.getClass().getName()+" : startXlet");
   }

   public void pauseXlet()
   {
      System.out.println(this.getClass().getName()+" : pauseXlet");
      context.notifyPaused();
   }

   public void destroyXlet(boolean unconditional) throws XletStateChangeException
   {
      if (unconditional)
      {
         if(scene != null)
         {
            scene.setVisible(false);
            scene.removeAll();
            HSceneFactory.getInstance().dispose(scene);
            scene = null;
         }

         context.notifyDestroyed();
         System.out.println(this.getClass().getName()+" : Xlet distrutta");
      }
      else
      {
         System.out.println(this.getClass().getName() +" : Richiesta di destroy rifiutata!");
         throw new XletStateChangeException("no thanks!");
      }
   }

   public void keyPressed(KeyEvent e)
   {
      switch(e.getKeyCode())
      {
         case 403:
            premutoRosso();
            break;
         case 404:
            premutoVerde();
            break;
         case 405:
            premutoGiallo();
            break;
         case 406:
            premutoBlu();
            break;
         case 37:
            spostaSinistra();
            break;
         case 38:
            spostaSu();
            break;
         case 39:
            spostaDestra();
            break;
         case 40:
            spostaGiu();
            break;
         case 10:
            premutoEnter();
            break;
         case 48:
            premutoZero();
            break;
         case 49:
            premutoUno();
            break;
         case 50:
            premutoDue();
            break;
         case 51:
            premutoTre();
            break;
         case 52:
            premutoQuattro();
            break;
         case 53:
            premutoCinque();
            break;
         case 54:
            premutoSei();
            break;
         case 55:
            premutoSette();
            break;
         case 56:
            premutoOtto();
            break;
         case 57:
            premutoNove();
            break;
         default:
            break;
      }
   }
   public void keyReleased(KeyEvent e) {}

   public void keyTyped(KeyEvent e){}

   public void premutoRosso()
   {
      switch(statoGenerale)
      {
         case 0:
         {
            switch(statoRosso)
            {
               case 0:
                  informazione[0].setVisible(false);
                  informazione[18].setVisible(false);
                  tf1.setBorder(line);
                  statoRosso++;
                  tf2.setBorder(matte);
                  informazione[1].setVisible(true);
                  informazione[20].setVisible(true);
                  break;
               case 1:
                  informazione[1].setVisible(false);
                  informazione[3].setVisible(false);
                  tf2.setBorder(line);
                  statoRosso++;
                  combo.setBorder(matte);
                  informazione[2].setVisible(true);
                  informazione[11].setVisible(true);
                  break;
               case 2:
                  informazione[2].setVisible(false);
                  informazione[8].setVisible(false);
                  informazione[17].setVisible(false);
                  informazione[20].setVisible(false);
                  informazione[11].setVisible(false);
                  combo.setBorder(line);
                  statoRosso++;
                  button1.setBorder(matte);
                  combo.hidePopup();
                  statoCombo = 0;
                  informazione[6].setVisible(true);
                  informazione[7].setVisible(true);
                  informazione[21].setVisible(true);
                  informazione[19].setVisible(true);
                  informazione[22].setVisible(true);
                  break;
               case 3:
                  informazione[6].setVisible(false);
                  informazione[7].setVisible(false);
                  informazione[21].setVisible(false);
                  informazione[19].setVisible(false);
                  informazione[22].setVisible(false);
                  button1.setBorder(line);
                  statoRosso = 0;
                  tf1.setBorder(matte);
                  cqueryerr.setVisible(false);
                  scene.repaint();
                  informazione[0].setVisible(true);
                  informazione[3].setVisible(true);
                  informazione[8].setVisible(true);
                  informazione[17].setVisible(true);
                  informazione[18].setVisible(true);
                  break;
               default:
                  break;
            }
            break;
         }
         case 1:
         {
            switch(statoRosso)
            {
               case 4:
                  informazione[12].setVisible(false);
                  informazione[27].setVisible(false);
                  tutente.setBorder(line);
                  statoRosso++;
                  tlibro.setBorder(matte);
                  informazione[13].setVisible(true);
                  informazione[28].setVisible(true);
                  break;
               case 5:
                  informazione[13].setVisible(false);
                  informazione[14].setVisible(false);
                  informazione[25].setVisible(false);
                  informazione[26].setVisible(false);
                  informazione[28].setVisible(false);
                  tlibro.setBorder(line);
                  statoRosso++;
                  bquery.setBorder(matte);
                  informazione[6].setVisible(true);
                  informazione[7].setVisible(true);
                  informazione[29].setVisible(true);
                  informazione[30].setVisible(true);
                  informazione[31].setVisible(true);
                  break;
               case 6:
                  bquery.setBorder(line);
                  informazione[6].setVisible(false);
                  informazione[7].setVisible(false);
                  informazione[29].setVisible(false);
                  informazione[30].setVisible(false);
                  informazione[31].setVisible(false);
                  statoRosso = 4;
                  tutente.setBorder(matte);
                  informazione[12].setVisible(true);
                  informazione[14].setVisible(true);
                  informazione[25].setVisible(true);
                  informazione[26].setVisible(true);
                  informazione[27].setVisible(true);
                  break;
               default:
                  break;
            }
         break;
         }
         default:
            break;
      }
   }

   public void premutoBlu()
   {
      if(statoGenerale == 0 && statoRosso == 2)
      {
         combo.hidePopup();
         statoCombo = 0;
      }
      if(statoGenerale == 0 || statoGenerale == 1 || statoGenerale == 4)
      {
         gen = statoGenerale;
         red = statoRosso;
         statoGenerale = 5;
         statoRosso = 9;
         besci.setBorder(matte);
         cesci.setVisible(true);
         scene.repaint();
      }
   }
   public void premutoVerde()
   {
      switch(statoGenerale)
      {
         case 0:
         {
            switch(statoRosso)
            {
               case 0:
                  informazione[0].setVisible(false);
                  informazione[3].setVisible(false);
                  informazione[8].setVisible(false);
                  informazione[17].setVisible(false);
                  informazione[18].setVisible(false);
                  tf1.setBorder(line);
                  statoRosso = 3;
                  button1.setBorder(matte);
                  informazione[6].setVisible(true);
                  informazione[7].setVisible(true);
                  informazione[21].setVisible(true);
                  informazione[19].setVisible(true);
                  informazione[22].setVisible(true);
                  break;
               case 1:
                  informazione[1].setVisible(false);
                  informazione[20].setVisible(false);
                  tf2.setBorder(line);
                  statoRosso--;
                  tf1.setBorder(matte);
                  informazione[0].setVisible(true);
                  informazione[18].setVisible(true);
                  break;
               case 2:
                  informazione[2].setVisible(false);
                  informazione[11].setVisible(false);
                  combo.setBorder(line);
                  statoRosso--;
                  tf2.setBorder(matte);
                  combo.hidePopup();
                  statoCombo = 0;
                  informazione[1].setVisible(true);
                  informazione[3].setVisible(true);
                  break;
               case 3:
                  informazione[6].setVisible(false);
                  informazione[7].setVisible(false);
                  informazione[21].setVisible(false);
                  informazione[19].setVisible(false);
                  informazione[22].setVisible(false);
                  button1.setBorder(line);
                  statoRosso--;
                  combo.setBorder(matte);
                  cqueryerr.setVisible(false);
                  scene.repaint();
                  informazione[2].setVisible(true);
                  informazione[11].setVisible(true);
                  informazione[8].setVisible(true);
                  informazione[17].setVisible(true);
                  informazione[20].setVisible(true);
                  break;
               default:
                  break;
            }
            break;
         }
         case 1:
         {
            switch(statoRosso)
            {
               case 4:
                  informazione[12].setVisible(false);
                  informazione[14].setVisible(false);
                  informazione[25].setVisible(false);
                  informazione[26].setVisible(false);
                  informazione[27].setVisible(false);
                  tutente.setBorder(line);
                  statoRosso = 6;
                  bquery.setBorder(matte);
                  informazione[6].setVisible(true);
                  informazione[7].setVisible(true);
                  informazione[29].setVisible(true);
                  informazione[30].setVisible(true);
                  informazione[31].setVisible(true);
                  break;
               case 5:
                  informazione[13].setVisible(false);
                  informazione[28].setVisible(false);
                  tlibro.setBorder(line);
                  statoRosso--;
                  tutente.setBorder(matte);
                  informazione[12].setVisible(true);
                  informazione[27].setVisible(true);
                  break;
               case 6:
                  informazione[6].setVisible(false);
                  informazione[7].setVisible(false);
                  informazione[29].setVisible(false);
                  informazione[30].setVisible(false);
                  informazione[31].setVisible(false);
                  bquery.setBorder(line);
                  statoRosso--;
                  tlibro.setBorder(matte);
                  informazione[13].setVisible(true);
                  informazione[14].setVisible(true);
                  informazione[25].setVisible(true);
                  informazione[26].setVisible(true);
                  informazione[28].setVisible(true);
                  break;
               default:
                  break;
            }
         break;
         }
         default:
            break;
      }
   }

   public void premutoGiallo()
   {
      if (statoGenerale == 1)
      {
         valori.clear();
         valori.trimToSize();
         System.out.println(valori.size());
         label.clear();
         label.trimToSize();
         System.out.println(label.size());
         cod.clear();
         cod.trimToSize();
         System.out.println(cod.size());
         button1.setBorder(line);
         bquery.setBorder(line);
         tlibro.setBorder(line);
         tf1.setText("");
         tf2.setText("");
         tutente.setText("");
         tlibro.setText("");
         try
         {
            initXlet(this.context);
         }
         catch(XletStateChangeException e)
         {
            e.printStackTrace(System.out);
         }
      }
   }
   public void premutoEnter()
   {
      switch(statoGenerale)
      {
         case 0:
         {
            switch(statoRosso)
            {
               case 0:
                  stampaTF1();
                  break;
               case 1:
                  stampaTF2();
                  break;
               case 2:
                  popCombo();
                  break;
               case 3:
                  unoDue();
                  break;
               default:
                  break;
            }
            break;
         }
         case 1:
         {
            if(statoRosso == 6)
               conferma();
            break;
         }
         case 2:
         {
            switch(statoRosso)
            {
               case 7:
                  continua();
                  break;
               case 8:
                  nonContinua();
                  break;
               default:
                  break;
            }
            break;
         }
         case 3:
            digitaOk();
            break;
         case 4:
            valori.clear();
            valori.trimToSize();
            System.out.println(valori.size());
            label.clear();
            label.trimToSize();
            System.out.println(label.size());
            cod.clear();
            cod.trimToSize();
            System.out.println(cod.size());
            button1.setBorder(line);
            bquery.setBorder(line);
            tf1.setText("");
            tf2.setText("");
            tutente.setText("");
            tlibro.setText("");
            try
            {
               initXlet(this.context);
            }
            catch(XletStateChangeException e)
            {
               e.printStackTrace(System.out);
            }
            break;
         case 5:
         {
            switch(statoRosso)
            {
               case 9:
                  try
                  {
                     destroyXlet(true);
                  }
                  catch(XletStateChangeException e)
                  {
                     e.printStackTrace(System.out);
                  }
                  break;
               case 10:
                  statoGenerale = gen;
                  statoRosso = red;
                  bnonesci.setBorder(line);
                  cesci.setVisible(false);
                  scene.repaint();
                  break;
               default:
                  break;
            }
            break;
         }
         default:
            break;
      }
   }

   private void premutoZero()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "0");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "0");
      }
   }
   private void premutoUno()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
         tutente.setText(tutente.getText() + "1");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "1");
      }
   }
   private void premutoDue()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "2");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "2");
      }
   }
   private void premutoTre()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "3");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "3");
      }
   }
   private void premutoQuattro()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "4");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "4");
      }
   }
   private void premutoCinque()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "5");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "5");
      }
   }
   private void premutoSei()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "6");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "6");
      }
   }
   private void premutoSette()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "7");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "7");
      }
   }
   private void premutoOtto()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "8");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "8");
      }
   }
   private void premutoNove()
   {
      if(statoGenerale == 1)
      {
         if(statoRosso == 4)
            tutente.setText(tutente.getText() + "9");
         if(statoRosso == 5)
            tlibro.setText(tlibro.getText() + "9");
      }
   }

   public void spostaSinistra()
   {
      switch(statoGenerale)
      {
         case 0:
            if(statoRosso == 0 || statoRosso == 1)
               sinistraTF();
            break;
         case 1:
            if(statoRosso == 4)
               cancellaUtente();
            if(statoRosso == 5)
               cancellaLibro();
         case 2:
            if(statoRosso == 8)
            {
               bno.setBorder(line);
               statoRosso--;
               bsi.setBorder(matte);
            }
            break;
         case 5:
            if(statoRosso == 10)
            {
               bnonesci.setBorder(line);
               statoRosso--;
               besci.setBorder(matte);
            }
            break;
         default:
            break;
      }
   }

   public void spostaSu()
   {
      if(statoGenerale == 0)
      {
         if(statoRosso == 0 || statoRosso == 1)
            suTF();
         if(statoRosso == 2)
            suCombo();
      }
   }

   public void spostaDestra()
   {
      switch(statoGenerale)
      {
         case 0:
            if(statoRosso == 0 || statoRosso == 1)
               destraTF();
            break;
         case 2:
            if(statoRosso == 7)
            {
               bsi.setBorder(line);
               statoRosso++;
               bno.setBorder(matte);
            }
            break;
         case 5:
            if(statoRosso == 9)
            {
               besci.setBorder(line);
               statoRosso++;
               bnonesci.setBorder(matte);
            }
         deafult:
            break;
      }
   }
   public void spostaGiu()
   {
      if(statoGenerale == 0)
      {
         if(statoRosso == 0 || statoRosso == 1)
            giuTF();
         if(statoRosso == 2)
            giuCombo();
      }
   }

   private void stampaTF1()
   {
      switch(statoTastiera)
      {
         case 0:
            if(isUp == true)
               tf1.setText(tf1.getText() + "Q");
            else
               tf1.setText(tf1.getText() + "q");
            break;
         case 1:
            if(isUp == true)
               tf1.setText(tf1.getText() + "W");
            else
               tf1.setText(tf1.getText() + "w");
            break;
         case 2:
            if(isUp == true)
               tf1.setText(tf1.getText() + "E");
            else
               tf1.setText(tf1.getText() + "e");
            break;
         case 3:
            if(isUp == true)
               tf1.setText(tf1.getText() + "R");
            else
               tf1.setText(tf1.getText() + "r");
            break;
         case 4:
            if(isUp == true)
               tf1.setText(tf1.getText() + "T");
            else
               tf1.setText(tf1.getText() + "t");
            break;
         case 5:
            if(isUp == true)
               tf1.setText(tf1.getText() + "Y");
            else
               tf1.setText(tf1.getText() + "y");
            break;
         case 6:
            if(isUp == true)
               tf1.setText(tf1.getText() + "U");
            else
               tf1.setText(tf1.getText() + "u");
            break;
         case 7:
            if(isUp == true)
               tf1.setText(tf1.getText() + "I");
            else
              tf1.setText(tf1.getText() + "i");
            break;
         case 8:
            if(isUp == true)
              tf1.setText(tf1.getText() + "O");
            else
              tf1.setText(tf1.getText() + "o");
            break;
         case 9:
            if(isUp == true)
               tf1.setText(tf1.getText() + "P");
            else
               tf1.setText(tf1.getText() + "p");
            break;
         case 10:
            if(isUp == true)
               tf1.setText(tf1.getText() + "A");
            else
               tf1.setText(tf1.getText() + "a");
            break;
         case 11:
            if(isUp == true)
               tf1.setText(tf1.getText() + "S");
            else
               tf1.setText(tf1.getText() + "s");
            break;
         case 12:
            if(isUp == true)
               tf1.setText(tf1.getText() + "D");
            else
               tf1.setText(tf1.getText() + "d");
            break;
         case 13:
            if(isUp == true)
               tf1.setText(tf1.getText() + "F");
            else
               tf1.setText(tf1.getText() + "f");
            break;
         case 14:
            if(isUp == true)
               tf1.setText(tf1.getText() + "G");
            else
               tf1.setText(tf1.getText() + "g");
            break;
         case 15:
            if(isUp == true)
               tf1.setText(tf1.getText() + "H");
            else
               tf1.setText(tf1.getText() + "h");
            break;
         case 16:
            if(isUp == true)
               tf1.setText(tf1.getText() + "J");
            else
               tf1.setText(tf1.getText() + "j");
            break;
         case 17:
            if(isUp == true)
               tf1.setText(tf1.getText() + "K");
            else
               tf1.setText(tf1.getText() + "k");
            break;
         case 18:
            if(isUp == true)
               tf1.setText(tf1.getText() + "L");
            else
               tf1.setText(tf1.getText() + "l");
            break;
         case 19:
               tf1.setText(tf1.getText() + "'");
            break;
         case 20:
            if(isUp == true)
            {
               shiftDown();
            }
            else
            {
               shiftUp();
            }
            break;
         case 21:
            if(isUp == true)
               tf1.setText(tf1.getText() + "Z");
            else
               tf1.setText(tf1.getText() + "z");
            break;
         case 22:
            if(isUp == true)
               tf1.setText(tf1.getText() + "X");
            else
               tf1.setText(tf1.getText() + "x");
            break;
         case 23:
            if(isUp == true)
               tf1.setText(tf1.getText() + "C");
            else
               tf1.setText(tf1.getText() + "c");
            break;
         case 24:
            if(isUp == true)
               tf1.setText(tf1.getText() + "V");
            else
               tf1.setText(tf1.getText() + "v");
            break;
         case 25:
            if(isUp == true)
               tf1.setText(tf1.getText() + "B");
            else
               tf1.setText(tf1.getText() + "b");
            break;
         case 26:
            if(isUp == true)
               tf1.setText(tf1.getText() + "N");
            else
               tf1.setText(tf1.getText() + "n");
            break;
         case 27:
            if(isUp == true)
               tf1.setText(tf1.getText() + "M");
            else
              tf1.setText(tf1.getText() + "m");
            break;
         case 28:
            tf1.setText(tf1.getText() + " ");
            break;
         case 29:
            String ut = tf1.getText();
            ut = ut.substring(0, ut.length() - 1);
            tf1.setText(ut);
            break;
         default:
            break;
      }
   }

   private void stampaTF2()
   {
      switch(statoTastiera)
      {
         case 0:
            if(isUp == true)
               tf2.setText(tf2.getText() + "Q");
            else
               tf2.setText(tf2.getText() + "q");
            break;
         case 1:
            if(isUp == true)
               tf2.setText(tf2.getText() + "W");
            else
               tf2.setText(tf2.getText() + "w");
            break;
         case 2:
            if(isUp == true)
               tf2.setText(tf2.getText() + "E");
            else
               tf2.setText(tf2.getText() + "e");
            break;
         case 3:
            if(isUp == true)
               tf2.setText(tf2.getText() + "R");
            else
               tf2.setText(tf2.getText() + "r");
            break;
         case 4:
            if(isUp == true)
               tf2.setText(tf2.getText() + "T");
            else
               tf2.setText(tf2.getText() + "t");
            break;
         case 5:
            if(isUp == true)
               tf2.setText(tf2.getText() + "Y");
            else
               tf2.setText(tf2.getText() + "y");
            break;
         case 6:
            if(isUp == true)
               tf2.setText(tf2.getText() + "U");
            else
               tf2.setText(tf2.getText() + "u");
            break;
         case 7:
            if(isUp == true)
               tf2.setText(tf2.getText() + "I");
            else
               tf2.setText(tf2.getText() + "i");
            break;
         case 8:
            if(isUp == true)
               tf2.setText(tf2.getText() + "O");
            else
               tf2.setText(tf2.getText() + "o");
            break;
         case 9:
            if(isUp == true)
               tf2.setText(tf2.getText() + "P");
            else
               tf2.setText(tf2.getText() + "p");
            break;
         case 10:
            if(isUp == true)
               tf2.setText(tf2.getText() + "A");
            else
               tf2.setText(tf2.getText() + "a");
            break;
         case 11:
            if(isUp == true)
               tf2.setText(tf2.getText() + "S");
            else
               tf2.setText(tf2.getText() + "s");
            break;
         case 12:
            if(isUp == true)
               tf2.setText(tf2.getText() + "D");
            else
               tf2.setText(tf2.getText() + "d");
            break;
         case 13:
            if(isUp == true)
               tf2.setText(tf2.getText() + "F");
            else
               tf2.setText(tf2.getText() + "f");
            break;
         case 14:
            if(isUp == true)
               tf2.setText(tf2.getText() + "G");
            else
               tf2.setText(tf2.getText() + "g");
            break;
         case 15:
            if(isUp == true)
               tf2.setText(tf2.getText() + "H");
            else
               tf2.setText(tf2.getText() + "h");
            break;
         case 16:
            if(isUp == true)
               tf2.setText(tf2.getText() + "J");
            else
               tf2.setText(tf2.getText() + "j");
            break;
         case 17:
            if(isUp == true)
               tf2.setText(tf2.getText() + "K");
           else
               tf2.setText(tf2.getText() + "k");
            break;
         case 18:
            if(isUp == true)
              tf2.setText(tf2.getText() + "L");
            else
               tf2.setText(tf2.getText() + "l");
            break;
         case 19:
            tf2.setText(tf2.getText() + "'");
           break;
         case 20:
            if(isUp == true)
            {
               shiftDown();
            }
            else
            {
               shiftUp();
            }
            break;
         case 21:
            if(isUp == true)
               tf2.setText(tf2.getText() + "Z");
            else
               tf2.setText(tf2.getText() + "z");
            break;
         case 22:
            if(isUp == true)
              tf2.setText(tf2.getText() + "X");
            else
               tf2.setText(tf2.getText() + "x");
            break;
         case 23:
            if(isUp == true)
               tf2.setText(tf2.getText() + "C");
            else
               tf2.setText(tf2.getText() + "c");
            break;
         case 24:
            if(isUp == true)
               tf2.setText(tf2.getText() + "V");
            else
               tf2.setText(tf2.getText() + "v");
            break;
         case 25:
            if(isUp == true)
               tf2.setText(tf2.getText() + "B");
            else
               tf2.setText(tf2.getText() + "b");
            break;
         case 26:
            if(isUp == true)
               tf2.setText(tf2.getText() + "N");
            else
               tf2.setText(tf2.getText() + "n");
            break;
         case 27:
            if(isUp == true)
               tf2.setText(tf2.getText() + "M");
            else
               tf2.setText(tf2.getText() + "m");
            break;
         case 28:
            tf2.setText(tf2.getText() + " ");
            break;
         case 29:
            String ut = tf2.getText();
            ut = ut.substring(0, ut.length() - 1);
            tf2.setText(ut);
            break;
         default:
            break;
      }
   }

   public void shiftDown()
   {
      isUp = false;
      for(int i = 0; i < 30; i++)
         cont.remove(tasto[i]);
      for(int i = 30; i < 60; i++)
         cont.add(tasto[i]);
      tasto[50].setBackground(Color.black);
      tasto[50].setForeground(Color.white);
      cont.add(text1);
      cont.add(text2);
      cont.add(text3);
      scene.add(cont);
      scene.setVisible(true);
      scene.requestFocus();
      scene.repaint();
   }
   public void shiftUp()
   {
      isUp = true;
      for(int i = 0; i < 30; i++)
         cont.add(tasto[i]);
      for(int i = 30; i < 60; i++)
         cont.remove(tasto[i]);
      cont.add(text1);
      cont.add(text2);
      cont.add(text3);
      scene.add(cont);
      scene.setVisible(true);
      scene.requestFocus();
      scene.repaint();
   }

   public void popCombo()
   {
      if(statoCombo == 0)
      {
         combo.showPopup();
         statoCombo = 1;
      }
      else
      {
         combo.hidePopup();
         statoCombo = 0;
      }
   }

   public void sinistraTF()
   {
      switch(statoTastiera)
      {
         case 0:
            statoTastiera = 9;
            if(isUp == true)
            {
               tasto[0].setForeground(Color.black);
               tasto[0].setBackground(Color.lightGray);
               tasto[9].setForeground(Color.white);
               tasto[9].setBackground(Color.black);
            }
            else
            {
               tasto[30].setBackground(Color.lightGray);
               tasto[30].setForeground(Color.black);
               tasto[39].setForeground(Color.white);
               tasto[39].setBackground(Color.black);
            }
            break;
         case 1:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[1].setForeground(Color.black);
               tasto[1].setBackground(Color.lightGray);
               tasto[0].setForeground(Color.white);
               tasto[0].setBackground(Color.black);
            }
            else
            {
               tasto[31].setBackground(Color.lightGray);
               tasto[31].setForeground(Color.black);
               tasto[30].setForeground(Color.white);
               tasto[30].setBackground(Color.black);
            }
            break;
         case 2:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[2].setBackground(Color.lightGray);
               tasto[2].setForeground(Color.black);
               tasto[1].setForeground(Color.white);
               tasto[1].setBackground(Color.black);
            }
            else
            {
               tasto[32].setBackground(Color.lightGray);
               tasto[32].setForeground(Color.black);
               tasto[31].setForeground(Color.white);
               tasto[31].setBackground(Color.black);
            }
            break;
         case 3:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[3].setBackground(Color.lightGray);
               tasto[3].setForeground(Color.black);
               tasto[2].setForeground(Color.white);
               tasto[2].setBackground(Color.black);
            }
            else
            {
               tasto[33].setBackground(Color.lightGray);
               tasto[33].setForeground(Color.black);
               tasto[32].setForeground(Color.white);
               tasto[32].setBackground(Color.black);
            }
            break;
         case 4:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[4].setBackground(Color.lightGray);
               tasto[4].setForeground(Color.black);
               tasto[3].setForeground(Color.white);
               tasto[3].setBackground(Color.black);
            }
            else
            {
               tasto[34].setBackground(Color.lightGray);
               tasto[34].setForeground(Color.black);
               tasto[33].setForeground(Color.white);
               tasto[33].setBackground(Color.black);
            }
            break;
         case 5:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[5].setBackground(Color.lightGray);
               tasto[5].setForeground(Color.black);
               tasto[4].setForeground(Color.white);
               tasto[4].setBackground(Color.black);
            }
            else
            {
               tasto[35].setBackground(Color.lightGray);
               tasto[35].setForeground(Color.black);
               tasto[34].setForeground(Color.white);
               tasto[34].setBackground(Color.black);
            }
            break;
         case 6:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[6].setBackground(Color.lightGray);
               tasto[6].setForeground(Color.black);
               tasto[5].setForeground(Color.white);
               tasto[5].setBackground(Color.black);
            }
            else
            {
               tasto[36].setBackground(Color.lightGray);
               tasto[36].setForeground(Color.black);
               tasto[35].setForeground(Color.white);
               tasto[35].setBackground(Color.black);
            }
            break;
         case 7:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[7].setBackground(Color.lightGray);
               tasto[7].setForeground(Color.black);
               tasto[6].setForeground(Color.white);
               tasto[6].setBackground(Color.black);
            }
            else
            {
               tasto[37].setBackground(Color.lightGray);
               tasto[37].setForeground(Color.black);
               tasto[36].setForeground(Color.white);
               tasto[36].setBackground(Color.black);
            }
            break;
         case 8:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[8].setBackground(Color.lightGray);
               tasto[8].setForeground(Color.black);
               tasto[7].setForeground(Color.white);
               tasto[7].setBackground(Color.black);
            }
            else
            {
               tasto[38].setBackground(Color.lightGray);
               tasto[38].setForeground(Color.black);
               tasto[37].setForeground(Color.white);
               tasto[37].setBackground(Color.black);
            }
            break;
         case 9:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[9].setBackground(Color.lightGray);
               tasto[9].setForeground(Color.black);
               tasto[8].setForeground(Color.white);
               tasto[8].setBackground(Color.black);
            }
            else
            {
               tasto[39].setBackground(Color.lightGray);
               tasto[39].setForeground(Color.black);
               tasto[38].setForeground(Color.white);
               tasto[38].setBackground(Color.black);
            }
            break;
         case 10:
            statoTastiera = 19;
            if(isUp == true)
            {
               tasto[10].setBackground(Color.lightGray);
               tasto[10].setForeground(Color.black);
               tasto[19].setForeground(Color.white);
               tasto[19].setBackground(Color.black);
            }
            else
            {
               tasto[40].setBackground(Color.lightGray);
               tasto[40].setForeground(Color.black);
               tasto[49].setForeground(Color.white);
               tasto[49].setBackground(Color.black);
            }
            break;
         case 11:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[11].setBackground(Color.lightGray);
               tasto[11].setForeground(Color.black);
               tasto[10].setForeground(Color.white);
               tasto[10].setBackground(Color.black);
            }
            else
            {
               tasto[41].setBackground(Color.lightGray);
               tasto[41].setForeground(Color.black);
               tasto[40].setForeground(Color.white);
               tasto[40].setBackground(Color.black);
            }
            break;
         case 12:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[12].setBackground(Color.lightGray);
               tasto[12].setForeground(Color.black);
               tasto[11].setForeground(Color.white);
               tasto[11].setBackground(Color.black);
            }
            else
            {
               tasto[42].setBackground(Color.lightGray);
               tasto[42].setForeground(Color.black);
               tasto[41].setForeground(Color.white);
               tasto[41].setBackground(Color.black);
            }
            break;
         case 13:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[13].setBackground(Color.lightGray);
               tasto[13].setForeground(Color.black);
               tasto[12].setForeground(Color.white);
               tasto[12].setBackground(Color.black);
            }
            else
            {
               tasto[43].setBackground(Color.lightGray);
               tasto[43].setForeground(Color.black);
               tasto[42].setForeground(Color.white);
               tasto[42].setBackground(Color.black);
            }
            break;
         case 14:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[14].setBackground(Color.lightGray);
               tasto[14].setForeground(Color.black);
               tasto[13].setForeground(Color.white);
               tasto[13].setBackground(Color.black);
            }
            else
            {
               tasto[44].setBackground(Color.lightGray);
               tasto[44].setForeground(Color.black);
               tasto[43].setForeground(Color.white);
               tasto[43].setBackground(Color.black);
            }
            break;
         case 15:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[15].setBackground(Color.lightGray);
               tasto[15].setForeground(Color.black);
               tasto[14].setForeground(Color.white);
               tasto[14].setBackground(Color.black);
            }
            else
            {
               tasto[45].setBackground(Color.lightGray);
               tasto[45].setForeground(Color.black);
               tasto[44].setForeground(Color.white);
               tasto[44].setBackground(Color.black);
            }
            break;
         case 16:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[16].setBackground(Color.lightGray);
               tasto[16].setForeground(Color.black);
               tasto[15].setForeground(Color.white);
               tasto[15].setBackground(Color.black);
            }
            else
            {
               tasto[46].setBackground(Color.lightGray);
               tasto[46].setForeground(Color.black);
               tasto[45].setForeground(Color.white);
               tasto[45].setBackground(Color.black);
            }
            break;
         case 17:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[17].setBackground(Color.lightGray);
               tasto[17].setForeground(Color.black);
               tasto[16].setForeground(Color.white);
               tasto[16].setBackground(Color.black);
            }
            else
            {
               tasto[47].setBackground(Color.lightGray);
               tasto[47].setForeground(Color.black);
               tasto[46].setForeground(Color.white);
               tasto[46].setBackground(Color.black);
            }
            break;
         case 18:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[18].setBackground(Color.lightGray);
               tasto[18].setForeground(Color.black);
               tasto[17].setForeground(Color.white);
               tasto[17].setBackground(Color.black);
            }
            else
            {
               tasto[48].setBackground(Color.lightGray);
               tasto[48].setForeground(Color.black);
               tasto[47].setForeground(Color.white);
               tasto[47].setBackground(Color.black);
            }
            break;
         case 19:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[19].setBackground(Color.lightGray);
               tasto[19].setForeground(Color.black);
               tasto[18].setForeground(Color.white);
               tasto[18].setBackground(Color.black);
            }
            else
            {
               tasto[49].setBackground(Color.lightGray);
               tasto[49].setForeground(Color.black);
               tasto[48].setForeground(Color.white);
               tasto[48].setBackground(Color.black);
            }
            break;
         case 20:
            statoTastiera = 29;
            if(isUp == true)
            {
               tasto[20].setBackground(Color.lightGray);
               tasto[20].setForeground(Color.black);
               tasto[29].setForeground(Color.white);
               tasto[29].setBackground(Color.black);
            }
            else
            {
               tasto[50].setBackground(Color.lightGray);
               tasto[50].setForeground(Color.black);
               tasto[59].setForeground(Color.white);
               tasto[59].setBackground(Color.black);
            }
            break;
         case 21:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[21].setBackground(Color.lightGray);
               tasto[21].setForeground(Color.black);
               tasto[20].setForeground(Color.white);
               tasto[20].setBackground(Color.black);
            }
            else
            {
               tasto[51].setBackground(Color.lightGray);
               tasto[51].setForeground(Color.black);
               tasto[50].setForeground(Color.white);
               tasto[50].setBackground(Color.black);
            }
            break;
         case 22:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[22].setBackground(Color.lightGray);
               tasto[22].setForeground(Color.black);
               tasto[21].setForeground(Color.white);
               tasto[21].setBackground(Color.black);
            }
            else
            {
               tasto[52].setBackground(Color.lightGray);
               tasto[52].setForeground(Color.black);
               tasto[51].setForeground(Color.white);
               tasto[51].setBackground(Color.black);
            }
            break;
         case 23:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[23].setBackground(Color.lightGray);
               tasto[23].setForeground(Color.black);
               tasto[22].setForeground(Color.white);
               tasto[22].setBackground(Color.black);
            }
            else
            {
               tasto[53].setBackground(Color.lightGray);
               tasto[53].setForeground(Color.black);
               tasto[52].setForeground(Color.white);
               tasto[52].setBackground(Color.black);
            }
            break;
         case 24:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[24].setBackground(Color.lightGray);
               tasto[24].setForeground(Color.black);
               tasto[23].setForeground(Color.white);
               tasto[23].setBackground(Color.black);
            }
            else
            {
               tasto[54].setBackground(Color.lightGray);
               tasto[54].setForeground(Color.black);
               tasto[53].setForeground(Color.white);
               tasto[53].setBackground(Color.black);
            }
            break;
         case 25:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[25].setBackground(Color.lightGray);
               tasto[25].setForeground(Color.black);
               tasto[24].setForeground(Color.white);
               tasto[24].setBackground(Color.black);
            }
            else
            {
               tasto[55].setBackground(Color.lightGray);
               tasto[55].setForeground(Color.black);
               tasto[54].setForeground(Color.white);
               tasto[54].setBackground(Color.black);
            }
            break;
         case 26:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[26].setBackground(Color.lightGray);
               tasto[26].setForeground(Color.black);
               tasto[25].setForeground(Color.white);
               tasto[25].setBackground(Color.black);
            }
            else
            {
               tasto[56].setBackground(Color.lightGray);
               tasto[56].setForeground(Color.black);
               tasto[55].setForeground(Color.white);
               tasto[55].setBackground(Color.black);
            }
            break;
         case 27:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[27].setBackground(Color.lightGray);
               tasto[27].setForeground(Color.black);
               tasto[26].setForeground(Color.white);
               tasto[26].setBackground(Color.black);
            }
            else
            {
               tasto[57].setBackground(Color.lightGray);
               tasto[57].setForeground(Color.black);
               tasto[56].setForeground(Color.white);
               tasto[56].setBackground(Color.black);
            }
            break;
         case 28:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[28].setBackground(Color.lightGray);
               tasto[28].setForeground(Color.black);
               tasto[27].setForeground(Color.white);
               tasto[27].setBackground(Color.black);
            }
            else
            {
               tasto[58].setBackground(Color.lightGray);
               tasto[58].setForeground(Color.black);
               tasto[57].setForeground(Color.white);
               tasto[57].setBackground(Color.black);
            }
            break;
         case 29:
            statoTastiera--;
            if(isUp == true)
            {
               tasto[29].setBackground(Color.lightGray);
               tasto[29].setForeground(Color.black);
               tasto[28].setForeground(Color.white);
               tasto[28].setBackground(Color.black);
            }
            else
            {
               tasto[59].setBackground(Color.lightGray);
               tasto[59].setForeground(Color.black);
               tasto[58].setForeground(Color.white);
               tasto[58].setBackground(Color.black);
            }
            break;
         default:
            break;
      }
   }
   public void suTF()
   {
      switch(statoTastiera)
      {
         case 0:
            statoTastiera = 20;
            if(isUp == true)
            {
               tasto[0].setBackground(Color.lightGray);
               tasto[0].setForeground(Color.black);
               tasto[20].setForeground(Color.white);
               tasto[20].setBackground(Color.black);
            }
            else
            {
               tasto[30].setBackground(Color.lightGray);
               tasto[30].setForeground(Color.black);
               tasto[50].setForeground(Color.white);
               tasto[50].setBackground(Color.black);
            }
            break;
         case 1:
            statoTastiera = 21;
            if(isUp == true)
            {
               tasto[1].setBackground(Color.lightGray);
               tasto[1].setForeground(Color.black);
               tasto[21].setForeground(Color.white);
               tasto[21].setBackground(Color.black);
            }
            else
            {
               tasto[31].setBackground(Color.lightGray);
               tasto[31].setForeground(Color.black);
               tasto[51].setForeground(Color.white);
               tasto[51].setBackground(Color.black);
            }
            break;
         case 2:
            statoTastiera = 22;
            if(isUp == true)
            {
               tasto[2].setBackground(Color.lightGray);
               tasto[2].setForeground(Color.black);
               tasto[22].setForeground(Color.white);
               tasto[22].setBackground(Color.black);
            }
            else
            {
               tasto[32].setBackground(Color.lightGray);
               tasto[32].setForeground(Color.black);
               tasto[52].setForeground(Color.white);
               tasto[52].setBackground(Color.black);
            }
            break;
         case 3:
            statoTastiera = 23;
            if(isUp == true)
            {
               tasto[3].setBackground(Color.lightGray);
               tasto[3].setForeground(Color.black);
               tasto[23].setForeground(Color.white);
               tasto[23].setBackground(Color.black);
            }
            else
            {
               tasto[33].setBackground(Color.lightGray);
               tasto[33].setForeground(Color.black);
               tasto[53].setForeground(Color.white);
               tasto[53].setBackground(Color.black);
            }
            break;
         case 4:
            statoTastiera = 24;
            if(isUp == true)
            {
               tasto[4].setBackground(Color.lightGray);
               tasto[4].setForeground(Color.black);
               tasto[24].setForeground(Color.white);
               tasto[24].setBackground(Color.black);
            }
            else
            {
               tasto[34].setBackground(Color.lightGray);
               tasto[34].setForeground(Color.black);
               tasto[54].setForeground(Color.white);
               tasto[54].setBackground(Color.black);
            }
            break;
         case 5:
            statoTastiera = 25;
            if(isUp == true)
            {
               tasto[5].setBackground(Color.lightGray);
               tasto[5].setForeground(Color.black);
               tasto[25].setForeground(Color.white);
               tasto[25].setBackground(Color.black);
            }
            else
            {
               tasto[35].setBackground(Color.lightGray);
               tasto[35].setForeground(Color.black);
               tasto[55].setForeground(Color.white);
               tasto[55].setBackground(Color.black);
            }
            break;
         case 6:
            statoTastiera = 26;
            if(isUp == true)
            {
               tasto[6].setBackground(Color.lightGray);
               tasto[6].setForeground(Color.black);
               tasto[26].setForeground(Color.white);
               tasto[26].setBackground(Color.black);
            }
            else
            {
               tasto[36].setBackground(Color.lightGray);
               tasto[36].setForeground(Color.black);
               tasto[56].setForeground(Color.white);
               tasto[56].setBackground(Color.black);
            }
            break;
         case 7:
            statoTastiera = 27;
            if(isUp == true)
            {
               tasto[7].setBackground(Color.lightGray);
               tasto[7].setForeground(Color.black);
               tasto[27].setForeground(Color.white);
               tasto[27].setBackground(Color.black);
            }
            else
            {
               tasto[37].setBackground(Color.lightGray);
               tasto[37].setForeground(Color.black);
               tasto[57].setForeground(Color.white);
               tasto[57].setBackground(Color.black);
            }
            break;
         case 8:
            statoTastiera = 28;
            if(isUp == true)
            {
               tasto[8].setBackground(Color.lightGray);
               tasto[8].setForeground(Color.black);
               tasto[28].setForeground(Color.white);
               tasto[28].setBackground(Color.black);
            }
            else
            {
               tasto[38].setBackground(Color.lightGray);
               tasto[38].setForeground(Color.black);
               tasto[58].setForeground(Color.white);
               tasto[58].setBackground(Color.black);
            }
            break;
         case 9:
            statoTastiera = 29;
            if(isUp == true)
            {
               tasto[9].setBackground(Color.lightGray);
               tasto[9].setForeground(Color.black);
               tasto[29].setForeground(Color.white);
               tasto[29].setBackground(Color.black);
            }
            else
            {
               tasto[39].setBackground(Color.lightGray);
               tasto[39].setForeground(Color.black);
               tasto[59].setForeground(Color.white);
               tasto[59].setBackground(Color.black);
            }
            break;
         case 10:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[10].setBackground(Color.lightGray);
               tasto[10].setForeground(Color.black);
               tasto[0].setForeground(Color.white);
               tasto[0].setBackground(Color.black);
            }
            else
            {
               tasto[40].setBackground(Color.lightGray);
               tasto[40].setForeground(Color.black);
               tasto[30].setForeground(Color.white);
               tasto[30].setBackground(Color.black);
            }
            break;
         case 11:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[11].setBackground(Color.lightGray);
               tasto[11].setForeground(Color.black);
               tasto[1].setForeground(Color.white);
               tasto[1].setBackground(Color.black);
            }
            else
            {
               tasto[41].setBackground(Color.lightGray);
               tasto[41].setForeground(Color.black);
               tasto[31].setForeground(Color.white);
               tasto[31].setBackground(Color.black);
            }
            break;
         case 12:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[12].setBackground(Color.lightGray);
               tasto[12].setForeground(Color.black);
               tasto[2].setForeground(Color.white);
               tasto[2].setBackground(Color.black);
            }
            else
            {
               tasto[42].setBackground(Color.lightGray);
               tasto[42].setForeground(Color.black);
               tasto[32].setForeground(Color.white);
               tasto[32].setBackground(Color.black);
            }
            break;
         case 13:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[13].setBackground(Color.lightGray);
               tasto[13].setForeground(Color.black);
               tasto[3].setForeground(Color.white);
               tasto[3].setBackground(Color.black);
            }
            else
            {
               tasto[43].setBackground(Color.lightGray);
               tasto[43].setForeground(Color.black);
               tasto[33].setForeground(Color.white);
               tasto[33].setBackground(Color.black);
            }
            break;
         case 14:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[14].setBackground(Color.lightGray);
               tasto[14].setForeground(Color.black);
               tasto[4].setForeground(Color.white);
               tasto[4].setBackground(Color.black);
            }
            else
            {
               tasto[44].setBackground(Color.lightGray);
               tasto[44].setForeground(Color.black);
               tasto[34].setForeground(Color.white);
               tasto[34].setBackground(Color.black);
            }
            break;
         case 15:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[15].setBackground(Color.lightGray);
               tasto[15].setForeground(Color.black);
               tasto[5].setForeground(Color.white);
               tasto[5].setBackground(Color.black);
            }
            else
            {
               tasto[45].setBackground(Color.lightGray);
               tasto[45].setForeground(Color.black);
               tasto[35].setForeground(Color.white);
               tasto[35].setBackground(Color.black);
            }
            break;
         case 16:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[16].setBackground(Color.lightGray);
               tasto[16].setForeground(Color.black);
               tasto[6].setForeground(Color.white);
               tasto[6].setBackground(Color.black);
            }
            else
            {
               tasto[46].setBackground(Color.lightGray);
               tasto[46].setForeground(Color.black);
               tasto[36].setForeground(Color.white);
               tasto[36].setBackground(Color.black);
            }
            break;
         case 17:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[17].setBackground(Color.lightGray);
               tasto[17].setForeground(Color.black);
               tasto[7].setForeground(Color.white);
               tasto[7].setBackground(Color.black);
            }
            else
            {
               tasto[47].setBackground(Color.lightGray);
               tasto[47].setForeground(Color.black);
               tasto[37].setForeground(Color.white);
               tasto[37].setBackground(Color.black);
            }
            break;
         case 18:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[18].setBackground(Color.lightGray);
               tasto[18].setForeground(Color.black);
               tasto[8].setForeground(Color.white);
               tasto[8].setBackground(Color.black);
            }
            else
            {
               tasto[48].setBackground(Color.lightGray);
               tasto[48].setForeground(Color.black);
               tasto[38].setForeground(Color.white);
               tasto[38].setBackground(Color.black);
            }
            break;
         case 19:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[19].setBackground(Color.lightGray);
               tasto[19].setForeground(Color.black);
               tasto[9].setForeground(Color.white);
               tasto[9].setBackground(Color.black);
            }
            else
            {
               tasto[49].setBackground(Color.lightGray);
               tasto[49].setForeground(Color.black);
               tasto[39].setForeground(Color.white);
               tasto[39].setBackground(Color.black);
            }
            break;
         case 20:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[20].setBackground(Color.lightGray);
               tasto[20].setForeground(Color.black);
               tasto[10].setForeground(Color.white);
               tasto[10].setBackground(Color.black);
            }
            else
            {
               tasto[50].setBackground(Color.lightGray);
               tasto[50].setForeground(Color.black);
               tasto[40].setForeground(Color.white);
               tasto[40].setBackground(Color.black);
            }
            break;
         case 21:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[21].setBackground(Color.lightGray);
               tasto[21].setForeground(Color.black);
               tasto[11].setForeground(Color.white);
               tasto[11].setBackground(Color.black);
            }
            else
            {
               tasto[51].setBackground(Color.lightGray);
               tasto[51].setForeground(Color.black);
               tasto[41].setForeground(Color.white);
               tasto[41].setBackground(Color.black);
            }
            break;
         case 22:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[22].setBackground(Color.lightGray);
               tasto[22].setForeground(Color.black);
               tasto[12].setForeground(Color.white);
               tasto[12].setBackground(Color.black);
            }
            else
            {
               tasto[52].setBackground(Color.lightGray);
               tasto[52].setForeground(Color.black);
               tasto[42].setForeground(Color.white);
               tasto[42].setBackground(Color.black);
            }
            break;
         case 23:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[23].setBackground(Color.lightGray);
               tasto[23].setForeground(Color.black);
               tasto[13].setForeground(Color.white);
               tasto[13].setBackground(Color.black);
            }
            else
            {
               tasto[53].setBackground(Color.lightGray);
               tasto[53].setForeground(Color.black);
               tasto[43].setForeground(Color.white);
               tasto[43].setBackground(Color.black);
            }
            break;
         case 24:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[24].setBackground(Color.lightGray);
               tasto[24].setForeground(Color.black);
               tasto[14].setForeground(Color.white);
               tasto[14].setBackground(Color.black);
            }
            else
            {
               tasto[54].setBackground(Color.lightGray);
               tasto[54].setForeground(Color.black);
               tasto[44].setForeground(Color.white);
               tasto[44].setBackground(Color.black);
            }
            break;
         case 25:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[25].setBackground(Color.lightGray);
               tasto[25].setForeground(Color.black);
               tasto[15].setForeground(Color.white);
               tasto[15].setBackground(Color.black);
            }
            else
            {
               tasto[55].setBackground(Color.lightGray);
               tasto[55].setForeground(Color.black);
               tasto[45].setForeground(Color.white);
               tasto[45].setBackground(Color.black);
            }
            break;
         case 26:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[26].setBackground(Color.lightGray);
               tasto[26].setForeground(Color.black);
               tasto[16].setForeground(Color.white);
               tasto[16].setBackground(Color.black);
            }
            else
            {
               tasto[56].setBackground(Color.lightGray);
               tasto[56].setForeground(Color.black);
               tasto[46].setForeground(Color.white);
               tasto[46].setBackground(Color.black);
            }
            break;
         case 27:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[27].setBackground(Color.lightGray);
               tasto[27].setForeground(Color.black);
               tasto[17].setForeground(Color.white);
               tasto[17].setBackground(Color.black);
            }
            else
            {
               tasto[57].setBackground(Color.lightGray);
               tasto[57].setForeground(Color.black);
               tasto[47].setForeground(Color.white);
               tasto[47].setBackground(Color.black);
            }
            break;
         case 28:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[28].setBackground(Color.lightGray);
               tasto[28].setForeground(Color.black);
               tasto[18].setForeground(Color.white);
               tasto[18].setBackground(Color.black);
            }
            else
            {
               tasto[58].setBackground(Color.lightGray);
               tasto[58].setForeground(Color.black);
               tasto[48].setForeground(Color.white);
               tasto[48].setBackground(Color.black);
            }
            break;
         case 29:
            statoTastiera -= 10;
            if(isUp == true)
            {
               tasto[29].setBackground(Color.lightGray);
               tasto[29].setForeground(Color.black);
               tasto[19].setForeground(Color.white);
               tasto[19].setBackground(Color.black);
            }
            else
            {
               tasto[59].setBackground(Color.lightGray);
               tasto[59].setForeground(Color.black);
               tasto[49].setForeground(Color.white);
               tasto[49].setBackground(Color.black);
            }
            break;
         default:
            break;
      }
   }
   public void destraTF()
   {
      switch(statoTastiera)
      {
         case 0:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[0].setBackground(Color.lightGray);
               tasto[0].setForeground(Color.black);
               tasto[1].setForeground(Color.white);
               tasto[1].setBackground(Color.black);
            }
            else
            {
               tasto[30].setBackground(Color.lightGray);
               tasto[30].setForeground(Color.black);
               tasto[31].setForeground(Color.white);
               tasto[31].setBackground(Color.black);
            }
            break;
         case 1:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[1].setBackground(Color.lightGray);
               tasto[1].setForeground(Color.black);
               tasto[2].setForeground(Color.white);
               tasto[2].setBackground(Color.black);
            }
            else
            {
               tasto[31].setBackground(Color.lightGray);
               tasto[31].setForeground(Color.black);
               tasto[32].setForeground(Color.white);
               tasto[32].setBackground(Color.black);
            }
            break;
         case 2:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[2].setBackground(Color.lightGray);
               tasto[2].setForeground(Color.black);
               tasto[3].setForeground(Color.white);
               tasto[3].setBackground(Color.black);
            }
            else
            {
               tasto[32].setBackground(Color.lightGray);
               tasto[32].setForeground(Color.black);
               tasto[33].setForeground(Color.white);
               tasto[33].setBackground(Color.black);
            }
            break;
         case 3:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[3].setBackground(Color.lightGray);
               tasto[3].setForeground(Color.black);
               tasto[4].setForeground(Color.white);
               tasto[4].setBackground(Color.black);
            }
            else
            {
               tasto[33].setBackground(Color.lightGray);
               tasto[33].setForeground(Color.black);
               tasto[34].setForeground(Color.white);
               tasto[34].setBackground(Color.black);
            }
            break;
         case 4:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[4].setBackground(Color.lightGray);
               tasto[4].setForeground(Color.black);
               tasto[5].setForeground(Color.white);
               tasto[5].setBackground(Color.black);
            }
            else
            {
               tasto[34].setBackground(Color.lightGray);
               tasto[34].setForeground(Color.black);
               tasto[35].setForeground(Color.white);
               tasto[35].setBackground(Color.black);
            }
            break;
         case 5:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[5].setBackground(Color.lightGray);
               tasto[5].setForeground(Color.black);
               tasto[6].setForeground(Color.white);
               tasto[6].setBackground(Color.black);
            }
            else
            {
               tasto[35].setBackground(Color.lightGray);
               tasto[35].setForeground(Color.black);
               tasto[36].setForeground(Color.white);
               tasto[36].setBackground(Color.black);
            }
            break;
         case 6:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[6].setBackground(Color.lightGray);
               tasto[6].setForeground(Color.black);
               tasto[7].setForeground(Color.white);
               tasto[7].setBackground(Color.black);
            }
            else
            {
               tasto[36].setBackground(Color.lightGray);
               tasto[36].setForeground(Color.black);
               tasto[37].setForeground(Color.white);
               tasto[37].setBackground(Color.black);
            }
            break;
         case 7:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[7].setBackground(Color.lightGray);
               tasto[7].setForeground(Color.black);
               tasto[8].setForeground(Color.white);
               tasto[8].setBackground(Color.black);
            }
            else
            {
               tasto[37].setBackground(Color.lightGray);
               tasto[37].setForeground(Color.black);
               tasto[38].setForeground(Color.white);
               tasto[38].setBackground(Color.black);
            }
            break;
         case 8:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[8].setBackground(Color.lightGray);
               tasto[8].setForeground(Color.black);
               tasto[9].setForeground(Color.white);
               tasto[9].setBackground(Color.black);
            }
            else
            {
               tasto[38].setBackground(Color.lightGray);
               tasto[38].setForeground(Color.black);
               tasto[39].setForeground(Color.white);
               tasto[39].setBackground(Color.black);
            }
            break;
         case 9:
            statoTastiera = 0;
            if(isUp == true)
            {
               tasto[9].setBackground(Color.lightGray);
               tasto[9].setForeground(Color.black);
               tasto[0].setForeground(Color.white);
               tasto[0].setBackground(Color.black);
            }
            else
            {
               tasto[39].setBackground(Color.lightGray);
               tasto[39].setForeground(Color.black);
               tasto[30].setForeground(Color.white);
               tasto[30].setBackground(Color.black);
            }
            break;
         case 10:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[10].setBackground(Color.lightGray);
               tasto[10].setForeground(Color.black);
               tasto[11].setForeground(Color.white);
               tasto[11].setBackground(Color.black);
            }
            else
            {
               tasto[40].setBackground(Color.lightGray);
               tasto[40].setForeground(Color.black);
               tasto[41].setForeground(Color.white);
               tasto[41].setBackground(Color.black);
            }
            break;
         case 11:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[11].setBackground(Color.lightGray);
               tasto[11].setForeground(Color.black);
               tasto[12].setForeground(Color.white);
               tasto[12].setBackground(Color.black);
            }
            else
            {
               tasto[41].setBackground(Color.lightGray);
               tasto[41].setForeground(Color.black);
               tasto[42].setForeground(Color.white);
               tasto[42].setBackground(Color.black);
            }
            break;
         case 12:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[12].setBackground(Color.lightGray);
               tasto[12].setForeground(Color.black);
               tasto[13].setForeground(Color.white);
               tasto[13].setBackground(Color.black);
            }
            else
            {
               tasto[42].setBackground(Color.lightGray);
               tasto[42].setForeground(Color.black);
               tasto[43].setForeground(Color.white);
               tasto[43].setBackground(Color.black);
            }
            break;
         case 13:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[13].setBackground(Color.lightGray);
               tasto[13].setForeground(Color.black);
               tasto[14].setForeground(Color.white);
               tasto[14].setBackground(Color.black);
            }
            else
            {
               tasto[43].setBackground(Color.lightGray);
               tasto[43].setForeground(Color.black);
               tasto[44].setForeground(Color.white);
               tasto[44].setBackground(Color.black);
            }
            break;
         case 14:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[14].setBackground(Color.lightGray);
               tasto[14].setForeground(Color.black);
               tasto[15].setForeground(Color.white);
               tasto[15].setBackground(Color.black);
            }
            else
            {
               tasto[44].setBackground(Color.lightGray);
               tasto[44].setForeground(Color.black);
               tasto[45].setForeground(Color.white);
               tasto[45].setBackground(Color.black);
            }
            break;
         case 15:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[15].setBackground(Color.lightGray);
               tasto[15].setForeground(Color.black);
               tasto[16].setForeground(Color.white);
               tasto[16].setBackground(Color.black);
            }
            else
            {
               tasto[45].setBackground(Color.lightGray);
               tasto[45].setForeground(Color.black);
               tasto[46].setForeground(Color.white);
               tasto[46].setBackground(Color.black);
            }
            break;
         case 16:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[16].setBackground(Color.lightGray);
               tasto[16].setForeground(Color.black);
               tasto[17].setForeground(Color.white);
               tasto[17].setBackground(Color.black);
            }
            else
            {
               tasto[46].setBackground(Color.lightGray);
               tasto[46].setForeground(Color.black);
               tasto[47].setForeground(Color.white);
               tasto[47].setBackground(Color.black);
            }
            break;
         case 17:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[17].setBackground(Color.lightGray);
               tasto[17].setForeground(Color.black);
               tasto[18].setForeground(Color.white);
               tasto[18].setBackground(Color.black);
            }
            else
            {
               tasto[47].setBackground(Color.lightGray);
               tasto[47].setForeground(Color.black);
               tasto[48].setForeground(Color.white);
               tasto[48].setBackground(Color.black);
            }
            break;
         case 18:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[18].setBackground(Color.lightGray);
               tasto[18].setForeground(Color.black);
               tasto[19].setForeground(Color.white);
               tasto[19].setBackground(Color.black);
            }
            else
            {
               tasto[48].setBackground(Color.lightGray);
               tasto[48].setForeground(Color.black);
               tasto[49].setForeground(Color.white);
               tasto[49].setBackground(Color.black);
            }
            break;
         case 19:
            statoTastiera = 10;
            if(isUp == true)
            {
               tasto[19].setBackground(Color.lightGray);
               tasto[19].setForeground(Color.black);
               tasto[10].setForeground(Color.white);
               tasto[10].setBackground(Color.black);
            }
            else
            {
               tasto[49].setBackground(Color.lightGray);
               tasto[49].setForeground(Color.black);
               tasto[40].setForeground(Color.white);
               tasto[40].setBackground(Color.black);
            }
            break;
         case 20:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[20].setBackground(Color.lightGray);
               tasto[20].setForeground(Color.black);
               tasto[21].setForeground(Color.white);
               tasto[21].setBackground(Color.black);
            }
            else
            {
               tasto[50].setBackground(Color.lightGray);
               tasto[50].setForeground(Color.black);
               tasto[51].setForeground(Color.white);
               tasto[51].setBackground(Color.black);
            }
            break;
         case 21:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[21].setBackground(Color.lightGray);
               tasto[21].setForeground(Color.black);
               tasto[22].setForeground(Color.white);
               tasto[22].setBackground(Color.black);
            }
            else
            {
               tasto[51].setBackground(Color.lightGray);
               tasto[51].setForeground(Color.black);
               tasto[52].setForeground(Color.white);
               tasto[52].setBackground(Color.black);
            }
            break;
         case 22:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[22].setBackground(Color.lightGray);
               tasto[22].setForeground(Color.black);
               tasto[23].setForeground(Color.white);
               tasto[23].setBackground(Color.black);
            }
            else
            {
               tasto[52].setBackground(Color.lightGray);
               tasto[52].setForeground(Color.black);
               tasto[53].setForeground(Color.white);
               tasto[53].setBackground(Color.black);
            }
            break;
         case 23:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[23].setBackground(Color.lightGray);
               tasto[23].setForeground(Color.black);
               tasto[24].setForeground(Color.white);
               tasto[24].setBackground(Color.black);
            }
            else
            {
               tasto[53].setBackground(Color.lightGray);
               tasto[53].setForeground(Color.black);
               tasto[54].setForeground(Color.white);
               tasto[54].setBackground(Color.black);
            }
            break;
         case 24:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[24].setBackground(Color.lightGray);
               tasto[24].setForeground(Color.black);
               tasto[25].setForeground(Color.white);
               tasto[25].setBackground(Color.black);
            }
            else
            {
               tasto[54].setBackground(Color.lightGray);
               tasto[54].setForeground(Color.black);
               tasto[55].setForeground(Color.white);
               tasto[55].setBackground(Color.black);
            }
            break;
         case 25:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[25].setBackground(Color.lightGray);
               tasto[25].setForeground(Color.black);
               tasto[26].setForeground(Color.white);
               tasto[26].setBackground(Color.black);
            }
            else
            {
               tasto[55].setBackground(Color.lightGray);
               tasto[55].setForeground(Color.black);
               tasto[56].setForeground(Color.white);
               tasto[56].setBackground(Color.black);
            }
            break;
         case 26:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[26].setBackground(Color.lightGray);
               tasto[26].setForeground(Color.black);
               tasto[27].setForeground(Color.white);
               tasto[27].setBackground(Color.black);
            }
            else
            {
               tasto[56].setBackground(Color.lightGray);
               tasto[56].setForeground(Color.black);
               tasto[57].setForeground(Color.white);
               tasto[57].setBackground(Color.black);
            }
            break;
         case 27:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[27].setBackground(Color.lightGray);
               tasto[27].setForeground(Color.black);
               tasto[28].setForeground(Color.white);
               tasto[28].setBackground(Color.black);
            }
            else
            {
               tasto[57].setBackground(Color.lightGray);
               tasto[57].setForeground(Color.black);
               tasto[58].setForeground(Color.white);
               tasto[58].setBackground(Color.black);
            }
            break;
         case 28:
            statoTastiera++;
            if(isUp == true)
            {
               tasto[28].setBackground(Color.lightGray);
               tasto[28].setForeground(Color.black);
               tasto[29].setForeground(Color.white);
               tasto[29].setBackground(Color.black);
            }
            else
            {
               tasto[58].setBackground(Color.lightGray);
               tasto[58].setForeground(Color.black);
               tasto[59].setForeground(Color.white);
               tasto[59].setBackground(Color.black);
            }
            break;
         case 29:
            statoTastiera = 20;
            if(isUp == true)
            {
               tasto[29].setBackground(Color.lightGray);
               tasto[29].setForeground(Color.black);
               tasto[20].setForeground(Color.white);
               tasto[20].setBackground(Color.black);
            }
            else
            {
               tasto[59].setBackground(Color.lightGray);
               tasto[59].setForeground(Color.black);
               tasto[50].setForeground(Color.white);
               tasto[50].setBackground(Color.black);
            }
            break;
         default:
            break;
      }
   }
   public void giuTF()
   {
      switch(statoTastiera)
      {
         case 0:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[0].setBackground(Color.lightGray);
               tasto[0].setForeground(Color.black);
               tasto[10].setForeground(Color.white);
               tasto[10].setBackground(Color.black);
            }
            else
            {
               tasto[30].setBackground(Color.lightGray);
               tasto[30].setForeground(Color.black);
               tasto[40].setForeground(Color.white);
               tasto[40].setBackground(Color.black);
            }
            break;
         case 1:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[1].setBackground(Color.lightGray);
               tasto[1].setForeground(Color.black);
               tasto[11].setForeground(Color.white);
               tasto[11].setBackground(Color.black);
            }
            else
            {
               tasto[31].setBackground(Color.lightGray);
               tasto[31].setForeground(Color.black);
               tasto[41].setForeground(Color.white);
               tasto[41].setBackground(Color.black);
            }
            break;
         case 2:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[2].setBackground(Color.lightGray);
               tasto[2].setForeground(Color.black);
               tasto[12].setForeground(Color.white);
               tasto[12].setBackground(Color.black);
            }
            else
            {
               tasto[32].setBackground(Color.lightGray);
               tasto[32].setForeground(Color.black);
               tasto[42].setForeground(Color.white);
               tasto[42].setBackground(Color.black);
            }
            break;
         case 3:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[3].setBackground(Color.lightGray);
               tasto[3].setForeground(Color.black);
               tasto[13].setForeground(Color.white);
               tasto[13].setBackground(Color.black);
            }
            else
            {
               tasto[33].setBackground(Color.lightGray);
               tasto[33].setForeground(Color.black);
               tasto[43].setForeground(Color.white);
               tasto[43].setBackground(Color.black);
            }
            break;
         case 4:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[4].setBackground(Color.lightGray);
               tasto[4].setForeground(Color.black);
               tasto[14].setForeground(Color.white);
               tasto[14].setBackground(Color.black);
            }
            else
            {
               tasto[34].setBackground(Color.lightGray);
               tasto[34].setForeground(Color.black);
               tasto[44].setForeground(Color.white);
               tasto[44].setBackground(Color.black);
            }
            break;
         case 5:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[5].setBackground(Color.lightGray);
               tasto[5].setForeground(Color.black);
               tasto[15].setForeground(Color.white);
               tasto[15].setBackground(Color.black);
            }
            else
            {
               tasto[35].setBackground(Color.lightGray);
               tasto[35].setForeground(Color.black);
               tasto[45].setForeground(Color.white);
               tasto[45].setBackground(Color.black);
            }
            break;
         case 6:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[6].setBackground(Color.lightGray);
               tasto[6].setForeground(Color.black);
               tasto[16].setForeground(Color.white);
               tasto[16].setBackground(Color.black);
            }
            else
            {
               tasto[36].setBackground(Color.lightGray);
               tasto[36].setForeground(Color.black);
               tasto[46].setForeground(Color.white);
               tasto[46].setBackground(Color.black);
            }
            break;
         case 7:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[7].setBackground(Color.lightGray);
               tasto[7].setForeground(Color.black);
               tasto[17].setForeground(Color.white);
               tasto[17].setBackground(Color.black);
            }
            else
            {
               tasto[37].setBackground(Color.lightGray);
               tasto[37].setForeground(Color.black);
               tasto[47].setForeground(Color.white);
               tasto[47].setBackground(Color.black);
            }
            break;
         case 8:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[8].setBackground(Color.lightGray);
               tasto[8].setForeground(Color.black);
               tasto[18].setForeground(Color.white);
               tasto[18].setBackground(Color.black);
            }
            else
            {
               tasto[38].setBackground(Color.lightGray);
               tasto[38].setForeground(Color.black);
               tasto[48].setForeground(Color.white);
               tasto[48].setBackground(Color.black);
            }
            break;
         case 9:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[9].setBackground(Color.lightGray);
               tasto[9].setForeground(Color.black);
               tasto[19].setForeground(Color.white);
               tasto[19].setBackground(Color.black);
            }
            else
            {
               tasto[39].setBackground(Color.lightGray);
               tasto[39].setForeground(Color.black);
               tasto[49].setForeground(Color.white);
               tasto[49].setBackground(Color.black);
            }
            break;
         case 10:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[10].setBackground(Color.lightGray);
               tasto[10].setForeground(Color.black);
               tasto[20].setForeground(Color.white);
               tasto[20].setBackground(Color.black);
            }
            else
            {
               tasto[40].setBackground(Color.lightGray);
               tasto[40].setForeground(Color.black);
               tasto[50].setForeground(Color.white);
               tasto[50].setBackground(Color.black);
            }
            break;
         case 11:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[11].setBackground(Color.lightGray);
               tasto[11].setForeground(Color.black);
               tasto[21].setForeground(Color.white);
               tasto[21].setBackground(Color.black);
            }
            else
            {
               tasto[41].setBackground(Color.lightGray);
               tasto[41].setForeground(Color.black);
               tasto[51].setForeground(Color.white);
               tasto[51].setBackground(Color.black);
            }
            break;
         case 12:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[12].setBackground(Color.lightGray);
               tasto[12].setForeground(Color.black);
               tasto[22].setForeground(Color.white);
               tasto[22].setBackground(Color.black);
            }
            else
            {
               tasto[42].setBackground(Color.lightGray);
               tasto[42].setForeground(Color.black);
               tasto[52].setForeground(Color.white);
               tasto[52].setBackground(Color.black);
            }
            break;
         case 13:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[13].setBackground(Color.lightGray);
               tasto[13].setForeground(Color.black);
               tasto[23].setForeground(Color.white);
               tasto[23].setBackground(Color.black);
            }
            else
            {
               tasto[43].setBackground(Color.lightGray);
               tasto[43].setForeground(Color.black);
               tasto[53].setForeground(Color.white);
               tasto[53].setBackground(Color.black);
            }
            break;
         case 14:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[14].setBackground(Color.lightGray);
               tasto[14].setForeground(Color.black);
               tasto[24].setForeground(Color.white);
               tasto[24].setBackground(Color.black);
            }
            else
            {
               tasto[44].setBackground(Color.lightGray);
               tasto[44].setForeground(Color.black);
               tasto[54].setForeground(Color.white);
               tasto[54].setBackground(Color.black);
            }
            break;
         case 15:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[15].setBackground(Color.lightGray);
               tasto[15].setForeground(Color.black);
               tasto[25].setForeground(Color.white);
               tasto[25].setBackground(Color.black);
            }
            else
            {
               tasto[45].setBackground(Color.lightGray);
               tasto[45].setForeground(Color.black);
               tasto[55].setForeground(Color.white);
               tasto[55].setBackground(Color.black);
            }
            break;
         case 16:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[16].setBackground(Color.lightGray);
               tasto[16].setForeground(Color.black);
               tasto[26].setForeground(Color.white);
               tasto[26].setBackground(Color.black);
            }
            else
            {
               tasto[46].setBackground(Color.lightGray);
               tasto[46].setForeground(Color.black);
               tasto[56].setForeground(Color.white);
               tasto[56].setBackground(Color.black);
            }
            break;
         case 17:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[17].setBackground(Color.lightGray);
               tasto[17].setForeground(Color.black);
               tasto[27].setForeground(Color.white);
               tasto[27].setBackground(Color.black);
            }
            else
            {
               tasto[47].setBackground(Color.lightGray);
               tasto[47].setForeground(Color.black);
               tasto[57].setForeground(Color.white);
               tasto[57].setBackground(Color.black);
            }
            break;
         case 18:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[18].setBackground(Color.lightGray);
               tasto[18].setForeground(Color.black);
               tasto[28].setForeground(Color.white);
               tasto[28].setBackground(Color.black);
            }
            else
            {
               tasto[48].setBackground(Color.lightGray);
               tasto[48].setForeground(Color.black);
               tasto[58].setForeground(Color.white);
               tasto[58].setBackground(Color.black);
            }
            break;
         case 19:
            statoTastiera += 10;
            if(isUp == true)
            {
               tasto[19].setBackground(Color.lightGray);
               tasto[19].setForeground(Color.black);
               tasto[29].setForeground(Color.white);
               tasto[29].setBackground(Color.black);
            }
            else
            {
               tasto[49].setBackground(Color.lightGray);
               tasto[49].setForeground(Color.black);
               tasto[59].setForeground(Color.white);
               tasto[59].setBackground(Color.black);
            }
            break;
         case 20:
            statoTastiera = 0;
            if(isUp == true)
            {
               tasto[20].setBackground(Color.lightGray);
               tasto[20].setForeground(Color.black);
               tasto[0].setForeground(Color.white);
               tasto[0].setBackground(Color.black);
            }
            else
            {
               tasto[50].setBackground(Color.lightGray);
               tasto[50].setForeground(Color.black);
               tasto[30].setForeground(Color.white);
               tasto[30].setBackground(Color.black);
            }
            break;
         case 21:
            statoTastiera = 1;
            if(isUp == true)
            {
               tasto[21].setBackground(Color.lightGray);
               tasto[21].setForeground(Color.black);
               tasto[1].setForeground(Color.white);
               tasto[1].setBackground(Color.black);
            }
            else
            {
               tasto[51].setBackground(Color.lightGray);
               tasto[51].setForeground(Color.black);
               tasto[31].setForeground(Color.white);
               tasto[31].setBackground(Color.black);
            }
            break;
         case 22:
            statoTastiera = 2;
            if(isUp == true)
            {
               tasto[22].setBackground(Color.lightGray);
               tasto[22].setForeground(Color.black);
               tasto[2].setForeground(Color.white);
               tasto[2].setBackground(Color.black);
            }
            else
            {
               tasto[52].setBackground(Color.lightGray);
               tasto[52].setForeground(Color.black);
               tasto[32].setForeground(Color.white);
               tasto[32].setBackground(Color.black);
            }
            break;
         case 23:
            statoTastiera = 3;
            if(isUp == true)
            {
               tasto[23].setBackground(Color.lightGray);
               tasto[23].setForeground(Color.black);
               tasto[3].setForeground(Color.white);
               tasto[3].setBackground(Color.black);
            }
            else
            {
               tasto[53].setBackground(Color.lightGray);
               tasto[53].setForeground(Color.black);
               tasto[33].setForeground(Color.white);
               tasto[33].setBackground(Color.black);
            }
            break;
         case 24:
            statoTastiera = 4;
            if(isUp == true)
            {
               tasto[24].setBackground(Color.lightGray);
               tasto[24].setForeground(Color.black);
               tasto[4].setForeground(Color.white);
               tasto[4].setBackground(Color.black);
            }
            else
            {
               tasto[54].setBackground(Color.lightGray);
               tasto[54].setForeground(Color.black);
               tasto[34].setForeground(Color.white);
               tasto[34].setBackground(Color.black);
            }
            break;
         case 25:
            statoTastiera = 5;
            if(isUp == true)
            {
               tasto[25].setBackground(Color.lightGray);
               tasto[25].setForeground(Color.black);
               tasto[5].setForeground(Color.white);
               tasto[5].setBackground(Color.black);
            }
            else
            {
               tasto[55].setBackground(Color.lightGray);
               tasto[55].setForeground(Color.black);
               tasto[35].setForeground(Color.white);
               tasto[35].setBackground(Color.black);
            }
            break;
         case 26:
            statoTastiera = 6;
            if(isUp == true)
            {
               tasto[26].setBackground(Color.lightGray);
               tasto[26].setForeground(Color.black);
               tasto[6].setForeground(Color.white);
               tasto[6].setBackground(Color.black);
            }
            else
            {
               tasto[56].setBackground(Color.lightGray);
               tasto[56].setForeground(Color.black);
               tasto[36].setForeground(Color.white);
               tasto[36].setBackground(Color.black);
            }
            break;
         case 27:
            statoTastiera = 7;
            if(isUp == true)
            {
               tasto[27].setBackground(Color.lightGray);
               tasto[27].setForeground(Color.black);
               tasto[7].setForeground(Color.white);
               tasto[7].setBackground(Color.black);
            }
            else
            {
               tasto[57].setBackground(Color.lightGray);
               tasto[57].setForeground(Color.black);
               tasto[37].setForeground(Color.white);
               tasto[37].setBackground(Color.black);
            }
            break;
         case 28:
            statoTastiera = 8;
            if(isUp == true)
            {
               tasto[28].setBackground(Color.lightGray);
               tasto[28].setForeground(Color.black);
               tasto[8].setForeground(Color.white);
               tasto[8].setBackground(Color.black);
            }
            else
            {
               tasto[58].setBackground(Color.lightGray);
               tasto[58].setForeground(Color.black);
               tasto[38].setForeground(Color.white);
               tasto[38].setBackground(Color.black);
            }
            break;
         case 29:
            statoTastiera = 9;
            if(isUp == true)
            {
               tasto[29].setBackground(Color.lightGray);
               tasto[29].setForeground(Color.black);
               tasto[9].setForeground(Color.white);
               tasto[9].setBackground(Color.black);
            }
            else
            {
               tasto[59].setBackground(Color.lightGray);
               tasto[59].setForeground(Color.black);
               tasto[39].setForeground(Color.white);
               tasto[39].setBackground(Color.black);
            }
            break;
         default:
            break;
      }
   }
   public void suCombo()
   {
      switch(statoBiblio)
      {
         case 0:
            combo.setSelectedItem(biblio[4]);
            statoBiblio = 4;
            break;
         case 1:
            combo.setSelectedItem(biblio[0]);
            statoBiblio--;
            break;
         case 2:
            combo.setSelectedItem(biblio[1]);
            statoBiblio--;
            break;
         case 3:
            combo.setSelectedItem(biblio[2]);
            statoBiblio--;
            break;
         case 4:
            combo.setSelectedItem(biblio[3]);
            statoBiblio--;
            break;
         default:
            break;
      }
   }
   public void giuCombo()
   {
      switch(statoBiblio)
      {
         case 0:
            combo.setSelectedItem(biblio[1]);
            statoBiblio++;
            break;
         case 1:
            combo.setSelectedItem(biblio[2]);
            statoBiblio++;
            break;
         case 2:
            combo.setSelectedItem(biblio[3]);
            statoBiblio++;
            break;
         case 3:
            combo.setSelectedItem(biblio[4]);
            statoBiblio++;
            break;
         case 4:
            combo.setSelectedItem(biblio[0]);
            statoBiblio = 0;
            break;
         default:
            break;
      }
   }
   public void unoDue()
   {
      primaQuery(tf1.getText(), tf2.getText(), (String)combo.getSelectedItem());
      if(valori.size() == 0)
      {
         System.out.println("La query non ha avuto risultato");
         cqueryerr.setVisible(true);
         scene.repaint();
      }
      else
      {
         statoGenerale = 1;
         statoRosso = 4;
         cont.removeAll();

         JLabel etaut = new JLabel("Autore");
         etaut.setForeground(Color.red);
         etaut.setBounds(60, 90, 200, 50);
         cont.add(etaut);
         JLabel ettit = new JLabel("Titolo");
         ettit.setForeground(Color.red);
         ettit.setBounds(167, 90, 200, 50);
         cont.add(ettit);
         JLabel etid = new JLabel("Codice Libro");
         etid.setForeground(Color.red);
         etid.setBounds(325, 90, 200, 50);
         cont.add(etid);

         for(int count = 0; count < valori.size(); count++)
            label.add(new JLabel((String)valori.get(count)));
         label.trimToSize();

         if(label.size() >= 3)
         {
            ((JLabel)label.get(0)).setBounds(60, 130, 200, 50);
            cont.add((JLabel)label.get(0));
            ((JLabel)label.get(1)).setBounds(167, 130, 200, 50);
            cont.add((JLabel)label.get(1));
            ((JLabel)label.get(2)).setBounds(325, 130, 200, 50);
            cont.add((JLabel)label.get(2));
         }
         if(label.size() >= 6)
         {
            ((JLabel)label.get(3)).setBounds(60, 160, 200, 50);
            cont.add((JLabel)label.get(3));
            ((JLabel)label.get(4)).setBounds(167, 160, 200, 50);
            cont.add((JLabel)label.get(4));
            ((JLabel)label.get(5)).setBounds(325, 160, 200, 50);
            cont.add((JLabel)label.get(5));
         }
         if(label.size() >= 9)
         {
            ((JLabel)label.get(6)).setBounds(60, 190, 200, 50);
            cont.add((JLabel)label.get(6));
            ((JLabel)label.get(7)).setBounds(167, 190, 200, 50);
            cont.add((JLabel)label.get(7));
            ((JLabel)label.get(8)).setBounds(325, 190, 200, 50);
            cont.add((JLabel)label.get(8));
         }
         if(label.size() >= 12)
         {
            ((JLabel)label.get(9)).setBounds(60, 220, 200, 50);
            cont.add((JLabel)label.get(9));
            ((JLabel)label.get(10)).setBounds(167, 220, 200, 50);
            cont.add((JLabel)label.get(10));
            ((JLabel)label.get(11)).setBounds(325, 220, 200, 50);
            cont.add((JLabel)label.get(11));
         }
         if(label.size() >= 15)
         {
            ((JLabel)label.get(12)).setBounds(60, 250, 200, 50);
            cont.add((JLabel)label.get(12));
            ((JLabel)label.get(13)).setBounds(167, 250, 200, 50);
            cont.add((JLabel)label.get(13));
            ((JLabel)label.get(14)).setBounds(325, 250, 200, 50);
            cont.add((JLabel)label.get(14));
         }
         if(label.size() >= 18)
         {
            ((JLabel)label.get(15)).setBounds(60, 280, 200, 50);
            cont.add((JLabel)label.get(15));
            ((JLabel)label.get(16)).setBounds(167, 280, 200, 50);
            cont.add((JLabel)label.get(16));
            ((JLabel)label.get(17)).setBounds(325, 280, 200, 50);
            cont.add((JLabel)label.get(17));
         }

         lutente.setBounds(60, 320, 200, 50);
         cont.add(lutente);
         tutente.setBounds(200, 335, 110, 25);
         tutente.setBackground(Color.lightGray);
         tutente.setBorder(matte);
         cont.add(tutente);
         llibro.setBounds(60, 365, 200, 50);
         cont.add(llibro);
         tlibro.setBounds(200, 380, 110, 25);
         tlibro.setBackground(Color.lightGray);
         tlibro.setBorder(line);
         cont.add(tlibro);
         bquery.setBounds(200, 420, 90, 26);
         bquery.setBackground(Color.lightGray);
         bquery.setBorder(line);
         cont.add(bquery);
         cont.add(informazione[6]);
         cont.add(informazione[7]);
         cont.add(informazione[12]);
         cont.add(informazione[13]);
         cont.add(informazione[14]);
         cont.add(informazione[15]);
         cont.add(informazione[16]);
         cont.add(informazione[23]);
         cont.add(informazione[24]);
         cont.add(informazione[25]);
         cont.add(informazione[26]);
         cont.add(informazione[27]);
         cont.add(informazione[28]);
         cont.add(informazione[29]);
         cont.add(informazione[30]);
         cont.add(informazione[31]);
         informazione[6].setVisible(false);
         informazione[7].setVisible(false);
         informazione[13].setVisible(false);
         informazione[28].setVisible(false);
         informazione[29].setVisible(false);
         informazione[30].setVisible(false);
         informazione[31].setVisible(false);

         cont.add(Botton);
         cont.add(text1);
         cont.add(text2);
         cont.add(text3bis);
         cont.add(text4);
         scene.add(cont);
         scene.setVisible(true);
         scene.repaint();
      }
   }
   public void primaQuery(String a, String t, String b)
   {
      System.out.println("L'autore inserito: " + a);
      System.out.println("Il titolo inserito: " + t);
      System.out.println("La biblioteca inserita: " + b);
      try
      {
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      }
      catch(ClassNotFoundException e)
      {
         System.out.println(e.toString());
         System.exit(1);
      }
      try
      {
         Connection conn = DriverManager.getConnection("jdbc:odbc:JDBCAccess");
         Statement stmt = conn.createStatement();
         String query="";
         if(t.length() > 1 && a.length() > 1 )
         {
            query = "SELECT Autore, Titolo, Codice_ID " +
               "FROM " + b +
               " WHERE Prenotazione = 0 AND " +
               "(Autore = '" + a + "' AND Titolo = '" + t + "')";
         }
         else if(t.length() < 1 && a.length() > 1)
         {
            query =  "SELECT Autore, Titolo, Codice_ID " +
               "FROM " + b +
               " WHERE Prenotazione = 0 AND " +
               "Autore = '" + a +"'";
         }
         else if(t.length() > 1 && a.length() < 1)
         {
            query =  "SELECT Autore, Titolo, Codice_ID " +
               "FROM " + b +
               " WHERE Prenotazione = 0 AND " +
               "Titolo = '" + t +"'";
         }
         else if(t.length() < 1 && a.length() < 1)
         {
            query = "SELECT * FROM " + b + " WHERE Autore = '" + a + "'";
         }
         System.out.println(query);
         ResultSet rs = stmt.executeQuery(query);
         int i = 0;
         while(rs.next())
         {
            valori.add(rs.getString(1));
            System.out.print((String)valori.get(i) + "    ");
            i++;
            valori.add(rs.getString(2));
            System.out.print((String)valori.get(i) + "    ");
            i++;
            valori.add(rs.getString(3));
            System.out.print((String)valori.get(i) + "    ");
            i++;
            System.out.println();
         }
         valori.trimToSize();
         System.out.println(i);
         System.out.println();
         rs.close();
         stmt.close();
         conn.close();
      }
      catch(SQLException se)
      {
         System.out.println(se.getMessage());
         se.printStackTrace(System.out);
         System.exit(1);
      }
   }

   public void cancellaUtente()
   {
      String ut = tutente.getText();
      ut = ut.substring(0, ut.length() - 1);
      tutente.setText(ut);
   }
   public void cancellaLibro()
   {
      String ut = tlibro.getText();
      ut = ut.substring(0, ut.length() - 1);
      tlibro.setText(ut);
   }
   public void fillEsci()
   {
      lesci.setBounds(68, 32, 200, 20);
      l1esci.setBounds(26, 75, 260, 20);
      l2esci.setBounds(68, 95, 260, 20);
      cesci.add(lesci);
      cesci.add(l1esci);
      cesci.add(l2esci);
      besci.setBounds(47, 127, 60, 30);
      besci.setBackground(Color.lightGray);
      cesci.add(besci);
      bnonesci.setBounds(164, 127, 60, 30);
      bnonesci.setBackground(Color.lightGray);
      cesci.add(bnonesci);
      cesci.add(tesci);
      cesci.add(tcesci);
   }
   public void fillQueryerr()
   {
      queryErrata.setBounds(40, 40, 270, 20);
      queryErrata2.setBounds(77, 95, 100, 20);
      lrossa1.setBounds(155, 95, 50, 20);
      lrossa1.setForeground(Color.red);
      queryErrata3.setBounds(77, 120, 270, 20);
      queryErrata.setForeground(Color.white);
      queryErrata2.setForeground(Color.white);
      queryErrata3.setForeground(Color.white);
      cqueryerr.add(queryErrata);
      cqueryerr.add(queryErrata2);
      cqueryerr.add(lrossa1);
      cqueryerr.add(queryErrata3);
      cqueryerr.add(tqueryerr);
   }
   public void fillConferma()
   {
      conferma.setBounds(32, 32, 300, 20);
      l6esci.setBounds(26, 75, 260, 20);
      l7esci.setBounds(68, 95, 260, 20);
      conferma.setForeground(Color.white);
      l6esci.setForeground(Color.white);
      l7esci.setForeground(Color.white);
      cconf.add(conferma);
      cconf.add(l6esci);
      cconf.add(l7esci);
      cconf.add(conferma);
      bsi.setBounds(47, 127, 60, 30);
      bsi.setBackground(Color.lightGray);
      cconf.add(bsi);
      bno.setBounds(164, 127, 60, 30);
      bno.setBackground(Color.lightGray);
      cconf.add(bno);
      cconf.add(tconf);
   }
   public void fillNonimm()
   {
      lnonimm.setBounds(19, 37, 400, 20);
      l3esci.setBounds(68, 85, 260, 20);
      lnonimm.setForeground(Color.white);
      l3esci.setForeground(Color.white);
      cnonimm.add(lnonimm);
      cnonimm.add(l3esci);
      bnonimm.setBounds(110, 120, 60, 30);
      bnonimm.setBackground(Color.lightGray);
      bnonimm.setBorder(matte);
      cnonimm.add(bnonimm);
      cnonimm.add(tnonimm);
   }
   public void fillErut()
   {
      errutente.setBounds(15, 37, 350, 20);
      l4esci.setBounds(68, 85, 260, 20);
      errutente.setForeground(Color.white);
      l4esci.setForeground(Color.white);
      cerut.add(errutente);
      cerut.add(l4esci);
      erbut.setBounds(110, 120, 60, 30);
      erbut.setBackground(Color.lightGray);
      erbut.setBorder(matte);
      cerut.add(erbut);
      cerut.add(terut);
   }
   public void fillErlib()
   {
      errlibro.setBounds(17, 37, 350, 20);
      l5esci.setBounds(68, 85, 260, 20);
      errlibro.setForeground(Color.white);
      l5esci.setForeground(Color.white);
      cerlib.add(errlibro);
      cerlib.add(l5esci);
      erblib.setBounds(110, 120, 60, 30);
      erblib.setBackground(Color.lightGray);
      erblib.setBorder(matte);
      cerlib.add(erblib);
      cerlib.add(terlib);
   }

   public void conferma()
   {
      red = statoRosso;
      statoGenerale = 2;
      statoRosso = 7;
      bsi.setBorder(matte);
      cconf.setVisible(true);
      scene.repaint();
   }

   public void nonContinua()
   {
      statoGenerale = 1;
      statoRosso = red;
      bno.setBorder(line);
      cconf.setVisible(false);
      scene.repaint();
   }
   public void continua()
   {
      cconf.setVisible(false);
      System.out.println("Codice utente : " + tutente.getText());
      System.out.println("Codice libro: " + tlibro.getText());
      if((tlibro.getText()).length() < 1 || (tutente.getText()).length() < 1)
      {
         statoGenerale = 3;
         cnonimm.setVisible(true);
         scene.repaint();
      }
      else
      {
      queryUtente(tutente.getText());
      if(rutente == false)
      {
         statoGenerale = 3;
         cerut.setVisible(true);
         scene.repaint();
      }
      else
      {
         queryLibro(tlibro.getText(), (String)combo.getSelectedItem(), tf1.getText(), tf2.getText());
         if(rlibro == false)
         {
            statoGenerale = 3;
            cerlib.setVisible(true);
            scene.repaint();
         }
         else
         {
            cont.removeAll();
            statoGenerale = 4;
            updateLibro(tlibro.getText(), (String)combo.getSelectedItem());
            JLabel libroGiusto = new JLabel("Il libro con codice " + tlibro.getText() + " e' stato prenotato");
            libroGiusto.setBounds(225, 225, 700, 50);
            cont.add(libroGiusto);
            JLabel torna = new JLabel("Premendo il pulsante puoi tornare alla ricerca di un altro libro");
            torna.setBounds(185, 320, 700, 50);
            cont.add(torna);
            iniziale.setBounds(257, 378, 190, 50);
            iniziale.setBackground(Color.lightGray);
            iniziale.setBorder(matte);
            cont.add(iniziale);
            cont.add(informazione[9]);
            informazione[9].setVisible(true);
            cont.add(informazione[10]);
            informazione[10].setVisible(true);
            Image imagesin;
            imagesin =Toolkit.getDefaultToolkit().getImage("libri3.gif");
            Botton1 = new HGraphicButton(imagesin,100,95,70,300);
            cont.add(Botton1);
            Image imageds;
            imageds =Toolkit.getDefaultToolkit().getImage("libri3.gif");
            Botton2 = new HGraphicButton(imageds,530,95,70,300);
            cont.add(Botton2);
            cont.add(Botton);
            cont.add(text1);
            cont.add(text2);
            cont.add(text3);
            scene.add(cont);
            scene.setVisible(true);
            scene.repaint();
            System.out.println(statoGenerale);
         }
      }
      }
   }

   public void digitaOk()
   {
      statoGenerale = 1;
      statoRosso = red;
      cnonimm.setVisible(false);
      cerut.setVisible(false);
      cerlib.setVisible(false);
      scene.repaint();
   }
   private void queryUtente(String qutente)
   {
      try
      {
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      }
      catch(ClassNotFoundException e)
      {
         System.out.println(e.toString());
         System.exit(1);
      }
      try
      {
         Connection conn = DriverManager.getConnection("jdbc:odbc:JDBCAccess");
         Statement stmt = conn.createStatement();
         String query= "SELECT CODICE_USER FROM UTENTI";
         ResultSet rs = stmt.executeQuery(query);
         String[] matr = new String[9];
         int i = 0;
         while(rs.next())
         {
            matr[i] = rs.getString(1);
            i++;
         }

         for(int count = 0; count < matr.length; count++)
         {
            if(qutente.equals(matr[count]))
               rutente = true;
         }

         rs.close();
         stmt.close();
         conn.close();
      }
      catch(SQLException se)
      {
         System.out.println(se.getMessage());
         se.printStackTrace(System.out);
         System.exit(1);
      }
   }
   private void queryLibro(String qlibro, String b, String text1, String text2)
   {
      try
      {
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      }
      catch(ClassNotFoundException e)
      {
         System.out.println(e.toString());
         System.exit(1);
      }
      try
      {
         Connection conn = DriverManager.getConnection("jdbc:odbc:JDBCAccess");
         Statement stmt = conn.createStatement();
         String query = "";
         if(text1.length() > 1 && text2.length() > 1 )
         {
            query = "SELECT Codice_ID " +
               "FROM " + b +
               " WHERE Prenotazione = 0 AND " +
               "(Autore = '" + text1 + "' AND Titolo = '" + text2 + "')";
         }
         else if(text2.length() < 1 && text1.length() > 1)
         {
            query =  "SELECT Codice_ID " +
               "FROM " + b +
               " WHERE Prenotazione = 0 AND " +
               "Autore = '" + text1 +"'";
         }
         else if(text2.length() > 1 && text1.length() < 1)
         {
            query =  "SELECT Codice_ID " +
               "FROM " + b +
               " WHERE Prenotazione = 0 AND " +
               "Titolo = '" + text2 +"'";
         }
         else if(text2.length() < 1 && text1.length() < 1)
         {
            query = "SELECT * FROM " + b + " WHERE Autore = '" + text1 + "'";
         }
         ResultSet rs = stmt.executeQuery(query);
         while(rs.next())
         {
            cod.add(rs.getString(1));
         }
         cod.trimToSize();
         for(int count = 0; count < cod.size(); count++)
            System.out.println((String)cod.get(count));
         for(int count = 0; count < cod.size(); count++)
         {
            if(qlibro.equals((String)cod.get(count)))
               rlibro = true;
         }

         rs.close();
         stmt.close();
         conn.close();
      }
      catch(SQLException se)
      {
         System.out.println(se.getMessage());
         se.printStackTrace(System.out);
         System.exit(1);
      }
   }
   private void updateLibro(String qlibro, String b)
   {
      try
      {
         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      }
      catch(ClassNotFoundException e)
      {
         System.out.println(e.toString());
         System.exit(1);
      }
      try
      {
         Connection conn = DriverManager.getConnection("jdbc:odbc:JDBCAccess");
         Statement stmt = conn.createStatement();
         stmt.executeUpdate("UPDATE " + b + " SET Prenotazione = 1 WHERE Codice_ID = " + qlibro);
         String query= "SELECT* FROM " + b;
         ResultSet rs = stmt.executeQuery(query);

         while(rs.next())
         {
            System.out.print(rs.getString(1) + "    ");
            System.out.print(rs.getString(2) + "    ");
            System.out.print(rs.getString(3) + "    ");
            System.out.print(rs.getString(4) + "    ");
            System.out.println();
         }

         rs.close();
         stmt.close();
         conn.close();
      }
      catch(SQLException se)
      {
         System.out.println(se.getMessage());
         se.printStackTrace(System.out);
         System.exit(1);
      }
   }
}
