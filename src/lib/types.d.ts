// src/lib/types.ts

export type Mail = {
  id: number | string;
  date: string;
  to: string;
  from: string;
  type: string;
  starred: boolean;
  selected: boolean;
  opened: boolean;
  thumbnail: string;         // URL to the thumbnail
  contentImage: string | null; // URL to opened image, or null if unopened
};

export type User = {
  id: number | string;
  name: string;
  email: string;
  avatar?: string; // URL to the user’s avatar (optional)
};

export type UserRequest = {
  id: number | string;
  text: string;   // e.g. “Shred Mail 543”
};
