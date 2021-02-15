<template>
    <div>
        <div class="container w-100 ml-4 mb-5">
            <h2 class="display-5 mt-5 green-text">{{ $t('FindingMedicQuickAndEasy') }}</h2>
        </div>
        <div class="container h-50 justify-content-center">
            <form class="filter-form p-3" @submit="submitForm">
                <div class="form-row">
                    <div class="col">
                        <h2 class="ml-5 mt-3 form-title">{{ $t('SearchMedics') }}</h2>
                    </div>
                </div>
                <div class="form-row justify-content-center justify-content-around mt-3">
                    <div class="col-5 pl-5">
                        <label for="name"></label>
                        <input class="w-100 form-control" type="text" name="name" id="name"
                               v-model="name"
                               :placeholder='$t("NameAndOrSurname")'>
                    </div>
                    <div class="col">
                        <label for="specialties"></label>
                        <select name="specialties" class="select-css form-control" id="specialties" v-model="specialtyId">
                            <option :value="null" disabled selected>{{ $t('Specialty') }}</option>
                            <option :value="null">{{ $t('Any') }}</option>
                            <option v-for="specialty in specialties" :key="specialty.id"
                                    :value="specialty.id">{{ specialty.name }}</option>
                        </select>
                    </div>
                    <div class="col pr-5">
                        <label for="localities">
                        </label>
                        <select name="localities" class="select-css form-control" id="localities" v-model="localityId">
                            <option :value="null" disabled selected>{{ $t('Locality') }}</option>
                            <option :value="null">{{ $t('Any') }}</option>
                            <option v-for="locality in localities" :key="locality.id"
                                    :value="locality.id">{{ locality.name }}</option>
                        </select>
                    </div>
                </div>
                <div class="form-row px-5 mt-4 mb-3">
                    <button
                        class="w-100 btn rounded-pill btn-light header-btn-element"
                        type="submit"
                    >
                        {{ $t('SearchMedics') }}
                    </button>
                </div>
            </form>
            <br>
            <div class="container"><hr></div>
            <div class="shareDiv">
                <h1 class="shareTitle">¡Compartí MediCare con tus amigos!</h1>
                <div class="shareSocial"></div>
                <!-- TODO:REDO THIS -->
                <br>
            </div>
            <br>
            <div class="container"><hr></div>
            <br>
            <br>
        </div>
    </div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import {State} from 'vuex-class';
import {localityActionTypes} from '~/store/types/localities.types';
import {doctorSpecialtyActionTypes} from '~/store/types/doctorSpecialties.types';
import {createPath, Hash, Nullable} from '~/logic/Utils';

@Component
export default class Landing extends Vue {
    @State(state => state.localities.localities)
    private readonly localities: [];
    @State(state => state.doctorSpecialties.doctorSpecialties)
    private readonly specialties: [];

    private name: string = '';
    private localityId: Nullable<number> = null;
    private specialtyId: Nullable<number> = null;

    mounted(): void {
        this.$store.dispatch('localities/loadLocalities', localityActionTypes.loadLocalities());
        this.$store.dispatch('doctorSpecialties/loadDoctorSpecialties', doctorSpecialtyActionTypes.loadDoctorSpecialties());
    }

    submitForm(event: Event): void {
        event.preventDefault();
        event.stopPropagation();

        this.search();
    }

    search(): void {
        let query: Hash<string | string[]> = {};
        if (this.name)
            query.name = this.name.trim();
        if (this.localityId)
            query.localities = [this.localityId.toString()];
        if (this.specialtyId)
            query.specialties = [this.specialtyId.toString()];

        this.$router.push({
            path: createPath("/mediclist"),
            query
        }).catch(()=>{});
    }
}
</script>


<style scoped>
.header-btn-element {
    color: #00C4BA;
    font-weight: bold;
}

.header-btn-element:hover {
    color: rgb(0, 160, 152);
    font-weight: bold;
}

.green-text {
    color: #00C4BA;
}

.filter-form {
    background-color: #00C4BA;
    border-radius: 1em;
}

.form-title {
    color: white;
}

.form-control {
    background-color: rgb(214, 214, 214);
}

.shareDiv {
    display: flex;
    padding: 0% 10%;
    justify-content: center;
}

.shareTitle {
    font-size: 1.5em;
    padding-top: 14px;
}

.shareSocial {
    margin-left: 30px;
}
</style>