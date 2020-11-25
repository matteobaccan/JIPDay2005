/*
 * @(#)testxlet.java      1.00 01/05/2005
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

import javax.tv.xlet.*;

public class testxlet implements Xlet {

   public void initXlet(XletContext context) throws XletStateChangeException
   {
      System.out.println( this.getClass().getName()+" : initXlet" );
   }

   public void startXlet() throws XletStateChangeException
   {
      System.out.println( this.getClass().getName()+" : startXlet" );
   }


   public void pauseXlet()
   {
      System.out.println( this.getClass().getName()+" : pauseXlet" );
   }

   public void destroyXlet(boolean unconditional) throws XletStateChangeException
   {
      System.out.println( this.getClass().getName()+" : destroyXlet" );
   }

}
