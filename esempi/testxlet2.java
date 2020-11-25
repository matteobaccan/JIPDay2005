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
// Xlet
import javax.tv.xlet.*;
// UI
import org.havi.ui.*;

public class testxlet2 implements Xlet {

   private XletContext context;
   private HScene scene;
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

      // Creo un container
      HContainer cont = new HContainer(50,50,650,500);

      // Creazione e aggiunta di una label
      HStaticText label = new HStaticText("CIAO MONDO", 100, 100, 200, 200, new Font("Tiresias", Font.BOLD, 22), Color.yellow, Color.black, new HDefaultTextLayoutManager());

      // Aggiungo la label al container
      cont.add(label);

      // aggiungo il container alla scena
      scene.add(cont);

      // Creo un secondo container
      HContainer cont2 = new HContainer(300,300,200,200);
      HStaticText label2 = new HStaticText("CIAO MONDO BIS", 1, 1, 190, 190, new Font("Tiresias", Font.BOLD, 22), Color.yellow, Color.black, new HDefaultTextLayoutManager());
      cont2.add(label2);
      scene.add(cont2);

      // Visibilita' e focus
      scene.setVisible(true);
      scene.requestFocus();
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

}
