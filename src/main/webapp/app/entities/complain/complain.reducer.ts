import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IComplain, defaultValue } from 'app/shared/model/complain.model';

export const ACTION_TYPES = {
  FETCH_COMPLAIN_LIST: 'complain/FETCH_COMPLAIN_LIST',
  FETCH_COMPLAIN: 'complain/FETCH_COMPLAIN',
  CREATE_COMPLAIN: 'complain/CREATE_COMPLAIN',
  UPDATE_COMPLAIN: 'complain/UPDATE_COMPLAIN',
  DELETE_COMPLAIN: 'complain/DELETE_COMPLAIN',
  SET_BLOB: 'complain/SET_BLOB',
  RESET: 'complain/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IComplain>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ComplainState = Readonly<typeof initialState>;

// Reducer

export default (state: ComplainState = initialState, action): ComplainState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COMPLAIN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COMPLAIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COMPLAIN):
    case REQUEST(ACTION_TYPES.UPDATE_COMPLAIN):
    case REQUEST(ACTION_TYPES.DELETE_COMPLAIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COMPLAIN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COMPLAIN):
    case FAILURE(ACTION_TYPES.CREATE_COMPLAIN):
    case FAILURE(ACTION_TYPES.UPDATE_COMPLAIN):
    case FAILURE(ACTION_TYPES.DELETE_COMPLAIN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPLAIN_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COMPLAIN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COMPLAIN):
    case SUCCESS(ACTION_TYPES.UPDATE_COMPLAIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COMPLAIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/complains';

// Actions

export const getEntities: ICrudGetAllAction<IComplain> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COMPLAIN_LIST,
    payload: axios.get<IComplain>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IComplain> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COMPLAIN,
    payload: axios.get<IComplain>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IComplain> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COMPLAIN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IComplain> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COMPLAIN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IComplain> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COMPLAIN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
