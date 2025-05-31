export type Mail = {
    id: number;
    date: string;
    to: string;
    from: string;
    type: string;
    starred: boolean;
    selected: boolean;
    opened: boolean;
    thumbnail: string;
    contentImage: string | null;
};
