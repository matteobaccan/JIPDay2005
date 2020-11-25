/*
 * @(#)testxlet5.java      1.00 30/09/2005
 *
 *  Copyright (C) 2005  Matteo Baccan <matteo@baccan.it>
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

// AWT
import java.awt.*;
import java.awt.event.*;
// Xlet
import javax.tv.xlet.*;
// UI
import org.havi.ui.*;
import org.havi.ui.event.*;

import org.dvb.event.*;

public class testxlet5 implements Xlet, UserEventListener, ActionListener {

   private XletContext context;
   private HScene scene;
   private UserEventRepository repository;
   private EventManager manager;
   private HStaticText label;
   private Choice choiceCurrent;
   private Choice choice;
   private Choice choice2;

   public void initXlet(XletContext context) throws XletStateChangeException
   {
      System.out.println( this.getClass().getName()+" : initXlet" );
      this.context = context;
   }

   public void startXlet() throws XletStateChangeException
   {
      System.out.println( this.getClass().getName()+" : startXlet" );

      // Instanza di HSceneFactory
      HSceneFactory hsceneFactory = HSceneFactory.getInstance();

      // Scena corrente
      scene = hsceneFactory.getFullScreenScene(HScreen.getDefaultHScreen().getDefaultHGraphicsDevice());

      // Dimensione del video
      scene.setSize(720, 576);

      // Nessun layout per posizionamento assoluto
      scene.setLayout(null);

      // Aggiunta di una combo
      choice = new Choice();
      choice.addItem("S.Anna");
      choice.addItem("S.Raffaele");
      choice.addItem("S.Eugenio");
      choice.reshape(100,50,200,70);
      scene.add(choice);
      label = new HStaticText("Ospedale", 10, 50, 100, 20, new Font("Tiresias", Font.BOLD, 14), Color.yellow, Color.black, new HDefaultTextLayoutManager());
      scene.add(label);
      label = new HStaticText("Premi tasto ROSSO e poi frecce", 250,50, 300, 20, new Font("Tiresias", Font.BOLD, 14), Color.yellow, Color.red, new HDefaultTextLayoutManager());
      scene.add(label);

      // Aggiunta di una combo
      choice2 = new Choice();
      choice2.addItem("Odontoiatrica");
      choice2.addItem("Oculistica");
      choice2.addItem("Ortopedica");
      choice2.addItem("Oncologica");
      choice2.reshape(100,80,200,100);
      scene.add(choice2);
      label = new HStaticText("Visita", 10, 80, 100, 20, new Font("Tiresias", Font.BOLD, 14), Color.yellow, Color.black, new HDefaultTextLayoutManager());
      scene.add(label);
      label = new HStaticText("Premi tasto VERDE e poi frecce", 250, 80, 300, 20, new Font("Tiresias", Font.BOLD, 14), Color.yellow, Color.green, new HDefaultTextLayoutManager());
      scene.add(label);

      // Creazione e aggiunta di una label
      label = new HStaticText("Prenotazione esami medici", 10, 5, 700, 40, new Font("Tiresias", Font.BOLD, 22), Color.yellow, Color.black, new HDefaultTextLayoutManager());
      scene.add(label);

      // Conferma
      Button b = new Button("PRENOTA");
      b.setBackground(Color.black);
      b.setForeground(Color.white);
      b.reshape(100,110,300,130);
      b.addActionListener( this );
      scene.add(b);

      // Carica immagine
      Image cinim = loadimage("892892.png");
      HStaticIcon cin = new HStaticIcon(cinim);
      cin.setBounds(10, 45, cinim.getWidth(null), cinim.getHeight(null));
      scene.add(cin);

      // Visibilita' e focus
      scene.setVisible(true);
      scene.requestFocus();

      // Gestione tasti
      repository = new UserEventRepository("UserRepository");
      repository.addAllColourKeys();
      repository.addAllNumericKeys();
      repository.addAllArrowKeys();
      repository.addKey(HRcEvent.VK_ENTER);

      // Gestione eventi
      manager = EventManager.getInstance();
      manager.addUserEventListener(this, repository);
   }

   public void pauseXlet()
   {
      System.out.println( this.getClass().getName()+" : pauseXlet" );
   }

   public void destroyXlet(boolean unconditional) throws XletStateChangeException
   {
      System.out.println( this.getClass().getName()+" : destroyXlet" );

      if (repository != null) {
         repository.removeAllColourKeys();
         if (manager != null) {
             manager.removeUserEventListener(this);
         }
      }

      // Se la Xlet e' partita, posso distruggere il layout
      if (scene != null) {
          scene.setVisible(false);
          scene.removeAll();
          scene = null;
      }

      context.notifyDestroyed();
   }

   public void userEventReceived(UserEvent event)
   {
      System.out.println ("KEY " + event.getCode());
      if(event.getType() == HRcEvent.KEY_RELEASED){
         switch(event.getCode())
         {
            case HRcEvent.VK_COLORED_KEY_0:
               System.out.println("Hai premuto rosso");
               choice.requestFocus();
               choiceCurrent = choice;
               break;
            case HRcEvent.VK_COLORED_KEY_1:
               System.out.println("Hai premuto verde");
               choice2.requestFocus();
               choiceCurrent = choice2;
               break;
            case HRcEvent.VK_COLORED_KEY_2:
               System.out.println("Hai premuto giallo");
               break;
            case HRcEvent.VK_COLORED_KEY_3:
               System.out.println("Hai premuto blu");
               break;
            case HRcEvent.VK_UP:
               choiceCurrent.select( choiceCurrent.getSelectedIndex()+1 );
               break;
            case HRcEvent.VK_DOWN:
               choiceCurrent.select( choiceCurrent.getSelectedIndex()-1 );
               break;
         }

         // repaint
         label.repaint();
      }
   }

    public void actionPerformed(ActionEvent event) {
       Button c = (Button)event.getSource();
       c.setLabel("PRENOTATO");
    }

    public Image loadimage(String fname)
    {
        System.out.println("DEB:loadimage:fname=" + fname);
        Image image = Toolkit.getDefaultToolkit().getImage(fname);
        try {
           while( image.getWidth(null)==-1 ) Thread.sleep( 500 );
        } catch (Throwable x) {}
        return image;
    }

}
