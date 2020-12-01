import {GenericEntity} from '~/src/logic/models/utils/GenericEntity';
import {JSONSerializableKeys} from '~/src/logic/models/utils/JSONSerializable';
import {User} from '~/src/logic/models/User';

export class Patient extends GenericEntity<Patient> {
    private _id: string;
    private _user: User;
    private _officeId: number;

    public get id(): string {
        return this._id;
    }

    public set id(value: string) {
        this._id = value;
    }

    public get user(): User {
        return this._user;
    }

    public set user(value: User) {
        this._user = value;
    }

    public get officeId(): number {
        return this._officeId;
    }

    public set officeId(value: number) {
        this._officeId = value;
    }

    public toJSON(): JSONSerializableKeys<Patient> {
        return {
            id: this.id,
            user: this.user,
            officeId: this.officeId
        };
    }
}
