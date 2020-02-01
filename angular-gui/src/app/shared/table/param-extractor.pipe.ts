import { ColumnSchema } from 'src/app/shared/table/column-schema.model';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'paramExtractor'
})
export class ParamExtractorPipe implements PipeTransform {

  transform(value: any, arg: ColumnSchema<undefined>): any {
    if (typeof arg.param === 'function') {
      return arg.param(value);
    }
    return value[arg.param ? arg.param : arg.key];
  }

}
