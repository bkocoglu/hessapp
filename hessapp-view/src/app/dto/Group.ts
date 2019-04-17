import {Spend} from "./Spend";
import {Participant} from "./Participant";
import {Message} from "./Message";

export class Group {
  groupID: string;
  name: string;
  description: string;
  moderator: string;
  participants: Participant[];
  spends: Spend[];
  messages: Message[];
  unaspiringMessageCount: number;
}
