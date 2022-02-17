package play.mickedplay.ctf.cmd;

import org.bukkit.command.CommandExecutor;
/**
 * Created by Spoony#0429 on 4.11.2017 at 11:29 UTC+4.
 * You are not allowed to remove this comment. Unless The Folowing Guidelines, Add me for the guidelinesss!
 */
public class Vote implements CommandExecutor {
}
@Override
       public boolean on onCommand (CommandSender sender, Command cmd, String label, String[] args){
  sender.sendMessage{"Players only";
    return true;|
            }
            if(args.length != 1)
              ChatUtil.sendMessage(p, "Voting In Progress ")
              ChatUtil.sendMessage(p, "Vote: [/vote <id>]") ;
            for(Map map : Map.getVoteMaps()) {}
               ChatUtil.sendMessage(p, map.getTempId() + " > " + map.getName () + " [" +
              ChatUtil.sendMessage{p, "------------------------------------- "}
              return true;
        }
        if(Vote.hasVoted()){
               ChatUtil.SendMessage(p, You Have Already Voted!)
        }

        String uname = p.getName () ;
        int id = Integer.parseInt (args[0]) - 1);
            Map map = Map.getMapById(id);
            Vote.vote (p, map);
            ChatUtil.sendMessage(p,"Voted for map '"+map.getName() + "a!")

             return true ;
        }
        }