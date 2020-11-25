/*
 * @(#)testxlet2.java      1.00 01/05/2005
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

public class testxlet3 implements Xlet, KeyListener {

   private XletContext context;
   private HScene scene;
   private HStaticText label;
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

      // Creazione e aggiunta di una label
      label = new HStaticText("CIAO MONDO", 100, 100, 200, 200, new Font("Tiresias", Font.BOLD, 22), Color.yellow, Color.black, new HDefaultTextLayoutManager());
      scene.add(label);

      // Visibilita' e focus
      scene.setVisible(true);
      scene.requestFocus();

      // Key listener
      scene.addKeyListener(this);
   }


   public void pauseXlet()
   {
      System.out.println( this.getClass().getName()+" : pauseXlet" );
   }

   public void destroyXlet(boolean unconditional) throws XletStateChangeException
   {
      System.out.println( this.getClass().getName()+" : destroyXlet" );

      // Se la Xlet e' partita, posso distruggere il layout
      if (scene != null) {
          scene.setVisible(false);
          scene.removeAll();
          scene = null;
      }

      context.notifyDestroyed();
   }

   // Gestione eventi alla AWT
   public void keyTyped(KeyEvent e) {}
   public void keyReleased(KeyEvent e) {}

   int nCol = 0;
   public void keyPressed(KeyEvent e) {
      // Nuovo colore e assegnazione
      nCol++;
      switch( nCol )
      {
          case 1: { label.setBackground(Color.red  ); break; }
          case 2: { label.setBackground(Color.blue ); break; }
          case 3: { label.setBackground(Color.black); break; }
      }
      if( nCol>=3 ) nCol = 0;

      // repaint
      label.repaint();
   }

}
