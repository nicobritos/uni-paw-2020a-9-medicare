import {Nullable} from '~/logic/Utils';
import {inject, injectable} from 'inversify';
import TYPES from '~/logic/types';
import {RestRepository} from '~/logic/interfaces/repositories/RestRepository';
import {getPathWithId} from '~/logic/services/Utils';
import {LocalityListParams, LocalityService} from '~/logic/interfaces/services/LocalityService';
import {Locality} from '~/logic/models/Locality';
import {APIError} from '~/logic/models/APIError';

const LocalityMIME = {
    LIST: 'application/vnd.locality.list.get.v1+json',
    GET: 'application/vnd.locality.get.v1+json',
};

@injectable()
export class LocalityServiceImpl implements LocalityService {
    private static PATH = 'localities';

    @inject(TYPES.Repositories.RestRepository)
    private rest: RestRepository;

    public async list(params?: LocalityListParams): Promise<Nullable<Locality[]> | APIError> {
        let response = await this.rest.get<Locality[]>(LocalityServiceImpl.PATH, {
            accepts: LocalityMIME.LIST,
            params
        });
        return response.nullableResponse;
    }

    public async get(id: number): Promise<Nullable<Locality>> {
        let response = await this.rest.get<Locality>(getPathWithId(LocalityServiceImpl.PATH, id), {
            accepts: LocalityMIME.GET
        });
        return response.orElse(null);
    }
}
