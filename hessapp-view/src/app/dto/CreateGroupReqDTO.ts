export class CreateGroupReqDTO {
  name: string;
  description: string;
  moderator: string;
  isPublic: boolean;
  participants: string[];
}
