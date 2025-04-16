export interface Showtime {
    sid: number;
    mid: number;
    startTime: Date;
    endTime: Date;
    price: number;
    totalSeats: number;
    bookedSeats: number;

    cinemaName: string;
    hallName: string;
}