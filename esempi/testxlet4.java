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
import org.havi.ui.event.*;

import org.dvb.event.*;

public class testxlet4 implements Xlet, UserEventListener {

   private XletContext context;
   private HScene scene;
   private UserEventRepository repository;
   private EventManager manager;
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

      // Gestione tasti
      repository = new UserEventRepository("UserRepository");
      repository.addAllColourKeys();

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
               label.setBackground(Color.red);
               break;
            case HRcEvent.VK_COLORED_KEY_1:
               System.out.println("Hai premuto verde");
               label.setBackground(Color.GREEN);
               break;
            case HRcEvent.VK_COLORED_KEY_2:
               System.out.println("Hai premuto giallo");
               label.setBackground(Color.YELLOW);
               break;
            case HRcEvent.VK_COLORED_KEY_3:
               System.out.println("Hai premuto blu");
               label.setBackground(Color.BLUE);
               break;
         }

         // repaint
         label.repaint();
      }
   }

}
