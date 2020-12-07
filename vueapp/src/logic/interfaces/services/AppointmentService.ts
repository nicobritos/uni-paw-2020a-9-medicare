import {APIError} from '~/logic/models/APIError';
import {Nullable} from '~/logic/models/utils/Utils';
import {Appointment} from '~/logic/models/Appointment';
import {DateRange} from '~/logic/models/utils/DateRange';

export interface CreateAppointment {
    date_from: Date;
    message?: string;
    motive?: string;
    doctorId: number;
}

export interface AppointmentService {
    list(dateRange: DateRange): Promise<Appointment[]>;

    get(id: number): Promise<Nullable<Appointment>>;

    create(appointment: CreateAppointment): Promise<Appointment | APIError>;

    delete(id: number): Promise<true | APIError>
}