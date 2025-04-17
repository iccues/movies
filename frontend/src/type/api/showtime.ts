export interface Showtime {
    sid?: number;
    mid: number;
    startTime: string | Date;
    endTime: string | Date;
    price: number;
    totalSeats: number;
    bookedSeats?: number;

    cinemaName: string;
    hallName: string;
}